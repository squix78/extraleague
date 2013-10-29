package ch.squix.extraleague.rest.matches;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.rest.games.GameDto;
import ch.squix.extraleague.rest.games.GameDtoMapper;
import ch.squix.extraleague.rest.games.GamesResource;



public class MatchesResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	
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
	public MatchDto create(MatchDto dto) {
		log.info("Received game to save");
		Match match = new Match();
		match.setEndDate(dto.getEndDate());
		match.setGameId(dto.getGameId());
		match.setStartDate(dto.getStartDate());
		match.setTeamA(dto.getTeamA());
		match.setTeamB(dto.getTeamB());
		match.setTeamAScore(dto.getTeamAScore());
		match.setTeamBScore(dto.getTeamBScore());
		ofy().save().entity(match).now();
		dto.setId(match.getId());
		return dto;
	}

}
