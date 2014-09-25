package ch.squix.extraleague.rest.games;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.model.playermarket.MeetingPointPlayer;
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.notification.UpdateMeetingPointMessage;
import ch.squix.extraleague.notification.UpdateOpenGamesMessage;
import ch.squix.extraleague.rest.games.mode.GameMode;
import ch.squix.extraleague.rest.games.mode.GameModeFactory;
import ch.squix.extraleague.rest.playermarket.MeetingPointPlayerMapper;

import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class GamesResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());


	@Get(value = "json")
	public List<GameDto> execute() throws UnsupportedEncodingException {
		String table = (String) this.getRequestAttributes().get("table");
		List<Game> games = ofy().load().type(Game.class).filter("table = ", table).list();
		log.info("Listing table for " + table + ". Found " + games.size() + " former games");
		List<GameDto> gameDtos = new ArrayList<>();
		for (Game game : games) {
			if (game.getEndDate() == null) {
				GameDto dto = GameDtoMapper.mapToDto(game);
				gameDtos.add(dto);
			}
		}
		return gameDtos;
	}
	
	@Post(value = "json")
	public GameDto create(GameDto dto) {
		
		GameMode mode = GameModeFactory.createGameMode(dto.getGameMode());
		
		log.info("Received game to save");
		Game game = new Game();
		game.setPlayers(dto.getPlayers());
		game.setTable(dto.getTable());
		game.setStartDate(new Date());
		game.setNumberOfCompletedMatches(0);
		game.setIsGameFinished(false);
		game.setGameMode(dto.getGameMode());
		mode.initializeGame(game); 
		game.setIndexOfLastUpdatedMatch(0);
		ofy().save().entity(game).now();
		
		dto.setId(game.getId());
		
	    
		
		// Prepare Matches
		List<Match> matches = mode.createMatches(game);
		ofy().save().entities(matches).now();
		NotificationService.sendMessage(new UpdateOpenGamesMessage(OpenGameService.getOpenGames()));
        List<PlayerUser> playersOfGame = ofy().load().type(PlayerUser.class).filter("player in", game.getPlayers()).list();
        
		Environment env = ApiProxy.getCurrentEnvironment();
		String hostname = (String) env.getAttributes().get("com.google.appengine.runtime.default_version_hostname");
        
        for (PlayerUser player : playersOfGame) {
            if (!Strings.isNullOrEmpty(player.getPushBulletApiKey())) {
                NotificationService.addPushBulletLinkMessageToSendQueue(
                        player.getPushBulletApiKey(),
                        "Extraleage game created",
                        "http://"+ hostname + "/#/games/" + game.getId(),
                        "The game with " + Joiner.on(", ").join(game.getPlayers()) + " was created.");
            }
            // more notifications possible here (email, ...)
        }
		removePlayersFromMeetingPoint(game.getPlayers());
		return dto;
	}

	private void removePlayersFromMeetingPoint(List<String> players) {
		List<MeetingPointPlayer> meetingPointPlayers = ofy().load().type(MeetingPointPlayer.class).list();
		List<MeetingPointPlayer> playersToDelete = new ArrayList<>();
		List<MeetingPointPlayer> remainingAtMeetingPoint = new ArrayList<>();
		
		for (MeetingPointPlayer meetingPointPlayer : meetingPointPlayers) {
			if (players.contains(meetingPointPlayer.getPlayer())) {
				playersToDelete.add(meetingPointPlayer);
			} else {
				remainingAtMeetingPoint.add(meetingPointPlayer);
			}
		}
		ofy().delete().entities(playersToDelete).now();
		NotificationService.sendMessage(new UpdateMeetingPointMessage(MeetingPointPlayerMapper.mapToDtos(remainingAtMeetingPoint)));
	}



}
