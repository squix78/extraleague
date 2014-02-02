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
			GameDto dto = GameDtoMapper.mapToDto(game);
			gameDtos.add(dto);
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
		ofy().save().entity(game).now();
		dto.setId(game.getId());
		
		// Prepare Matches
		List<Match> matches = createMatches(game);
		ofy().save().entities(matches).now();
		return dto;
	}

	public List<Match> createMatches(Game game) {
		List<String> players = game.getPlayers();
		List<Match> matches = new ArrayList<>();
		for (int gameIndex = 0; gameIndex < 4; gameIndex++) {
			Integer [] mutation = mutations[gameIndex];
			Match match = new Match();
			match.setGameId(game.getId());
			match.setTeamA(new String[] {players.get(mutation[0]), players.get(mutation[1])});
			match.setTeamB(new String[] {players.get(mutation[2]), players.get(mutation[3])});
			match.setTeamAScore(0);
			match.setTeamBScore(0);
			match.setTable(game.getTable());
			match.setMatchIndex(gameIndex);
			matches.add(match);
		}
		return matches;
	}

}
