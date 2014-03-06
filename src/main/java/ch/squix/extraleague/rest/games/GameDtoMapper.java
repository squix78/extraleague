package ch.squix.extraleague.rest.games;

import java.util.Date;

import ch.squix.extraleague.model.game.Game;

public class GameDtoMapper {
	
	public static GameDto mapToDto(Game game) {
		GameDto dto = new GameDto();
		dto.setId(game.getId());
		dto.setPlayers(game.getPlayers());
		dto.setTable(game.getTable());
		dto.setStartDate(game.getStartDate());
		dto.setEndDate(game.getEndDate());
		dto.setNumberOfCompletedGames(game.getNumberOfCompletedMatches());
		dto.setGameProgress(game.getGameProgress());
		if (game.getStartDate() != null && game.getEndDate() == null) {
			Double progress = game.getGameProgress();
			if (progress == null) {
				progress = 0d;
			}
			Long now = new Date().getTime();
			Long gameAge =  now - game.getStartDate().getTime();
			Long estimatedGameAge = Math.round((1 - progress) * gameAge);
			Date estimatedTimeOfArrival = new Date(now + estimatedGameAge);
			dto.setEstimatedTimeOfArrival(estimatedTimeOfArrival);
		}
		return dto;
	}

}
