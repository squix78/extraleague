package ch.squix.extraleague.rest.games;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.playermarket.MeetingPointPlayer;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.elo.EloUtil;
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.notification.UpdateMeetingPointMessage;
import ch.squix.extraleague.notification.UpdateOpenGamesMessage;
import ch.squix.extraleague.rest.playermarket.MeetingPointPlayerMapper;



public class GamesResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	private static final Integer [][] mutations = {{0,1,2,3}, {1, 2, 3, 0}, {2, 0, 3, 1}, {0, 3, 1, 2}};
	
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
		
		log.info("Received game to save");
		Game game = new Game();
		game.setPlayers(dto.getPlayers());
		game.setTable(dto.getTable());
		game.setStartDate(new Date());
		game.setNumberOfCompletedMatches(0);
		game.setIsGameFinished(false);
		ofy().save().entity(game).now();
		dto.setId(game.getId());
		
		// Prepare Matches
		List<Match> matches = createMatches(game);
		ofy().save().entities(matches).now();
		NotificationService.sendMessage(new UpdateOpenGamesMessage(OpenGameService.getOpenGames()));
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

	public List<Match> createMatches(Game game) {
	    Ranking currentRanking = ofy().load().type(Ranking.class).order("-createdDate").first().now();

		List<String> players = game.getPlayers();
		if (currentRanking != null) {
				players = sortPlayersByRanking(players, currentRanking);
		}
		List<Match> matches = new ArrayList<>();
		for (int gameIndex = 0; gameIndex < 4; gameIndex++) {
			Integer [] mutation = mutations[gameIndex];
			Match match = new Match();
			match.setGameId(game.getId());
			match.setTeamA(new String[] {players.get(mutation[0]), players.get(mutation[1])});
			match.setTeamB(new String[] {players.get(mutation[2]), players.get(mutation[3])});
			match.setTeamAScore(0);
			match.setTeamBScore(0);
			match.setPlayers(players);
			match.setTable(game.getTable());
			match.getTags().add(game.getTable());
			match.setMatchIndex(gameIndex);
			if (currentRanking != null) {
			    Double winProbabilityTeamA = EloUtil.getExpectedOutcome(getTeamRanking(currentRanking, match.getTeamA()), getTeamRanking(currentRanking, match.getTeamB()));
			    match.setWinProbabilityTeamA(winProbabilityTeamA);
			    Integer winPointsTeamA = EloUtil.calculateDelta(1d, winProbabilityTeamA);
			    match.setWinPointsTeamA(winPointsTeamA);
			    Integer winPointsTeamB = EloUtil.calculateDelta(1d, 1 - winProbabilityTeamA);
			    match.setWinPointsTeamB(winPointsTeamB);
			}
			matches.add(match);
		}
		return matches;
	}
	
	public List<String> sortPlayersByRanking(List<String> players, Ranking currentRanking) {
		Map<Integer, String> playerRankMap = new TreeMap<>();
		for (String player : players) {
			PlayerRanking playerRanking = currentRanking.getPlayerRanking(player);
			Integer rank = Integer.MAX_VALUE;
			if (playerRanking != null) {
				rank = playerRanking.getEloRanking();
			}
			playerRankMap.put(rank, player);
		}
		return new ArrayList<>(playerRankMap.values());
	}

	private Integer getTeamRanking(Ranking ranking, String[] team) {
	    return (int) Math.round((getPlayerRanking(ranking, team[0]) + getPlayerRanking(ranking, team[1])) / 2d);
	}
	
	private Integer getPlayerRanking(Ranking ranking, String player) {
	    PlayerRanking playerRanking = ranking.getPlayerRanking(player);
	    if (playerRanking != null && playerRanking.getEloValue() != null) {
	        return playerRanking.getEloValue();
	    }
	    return EloUtil.INITIAL_RATING;
	}

}
