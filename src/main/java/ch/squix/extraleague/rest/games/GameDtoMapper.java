package ch.squix.extraleague.rest.games;

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
		return dto;
	}

}
