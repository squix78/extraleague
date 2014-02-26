package ch.squix.extraleague.rest.games;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.game.Game;

public class OpenGameService {
	

	public static List<GameDto> getOpenGames() {
		List<Game> games = ofy().load().type(Game.class).list();
		List<GameDto> gameDtos = new ArrayList<>();
		for (Game game : games) {
			if (game.getEndDate() == null) {
				GameDto dto = GameDtoMapper.mapToDto(game);
				gameDtos.add(dto);
			}
		}
		return gameDtos;
	}

}
