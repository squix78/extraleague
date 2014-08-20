package ch.squix.extraleague.rest.games;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.squix.extraleague.model.game.Game;

public class OpenGameService {
	

	public static List<GameDto> getOpenGames() {
		List<Game> games = ofy().load().type(Game.class).filter("isGameFinished", "false").list();
		List<GameDto> gameDtos = new ArrayList<>();
		for (Game game : games) {
			if (game.getEndDate() == null) {
				GameDto dto = GameDtoMapper.mapToDto(game);
				gameDtos.add(dto);
			}
		}
		Collections.sort(gameDtos, new StartDateComparator());
		return gameDtos;
	}
	
	private static class StartDateComparator implements Comparator<GameDto> {

		@Override
		public int compare(GameDto o1, GameDto o2) {
			return o1.getStartDate().compareTo(o2.getStartDate());
		}
		
	}

}
