package ch.squix.extraleague.rest.games;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;


public class PlayedGamesResource extends ServerResource {
	
	
	@Get(value = "json")
	public static List<GameDto> getPlayedGames() {
		List<Game> games = ofy().load().type(Game.class).list();
		List<GameDto> gameDtos = new ArrayList<>();
		for (Game game : games) {
			if (LocalDate.fromDateFields(game.getStartDate()).isEqual(LocalDate.now())) {
				gameDtos.add(GameDtoMapper.mapToDto(game));
			}
		}
		return gameDtos;
	}

}
