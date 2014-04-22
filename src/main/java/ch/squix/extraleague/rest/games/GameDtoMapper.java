package ch.squix.extraleague.rest.games;

import java.util.Date;

import ch.squix.extraleague.model.game.Game;

public class GameDtoMapper {
	
	private static final Long AVERAGE_GAME_LENGTH_MILLIS = 4 * 350 * 1000L;

	public static GameDto mapToDto(Game game) {
		GameDto dto = new GameDto();
		dto.setId(game.getId());
		dto.setPlayers(game.getPlayers());
		dto.setTable(game.getTable());
		dto.setStartDate(game.getStartDate());
		dto.setEndDate(game.getEndDate());
		dto.setNumberOfCompletedGames(game.getNumberOfCompletedMatches());
		dto.setGameProgress(game.getGameProgress());
		dto.setFirstGoalDate(game.getFirstGoalDate());
		dto.setIsGameFinished(game.isGameFinished());
		if (game.getFirstGoalDate() != null && game.getEndDate() == null) {
			Double progress = game.getGameProgress();
			if (progress == null) {
				progress = 0d;
			}
			Long now = new Date().getTime();
			Long durationSinceFirstGoal =  now - game.getFirstGoalDate().getTime();
			// only calculate
			if (durationSinceFirstGoal > 10000 && progress >= 0.1 ) {
				Long estimatedRemainingMillis = Math.round((1 - progress) * durationSinceFirstGoal / (progress - 0.05));
				dto.setEstimatedRemainingMillis(estimatedRemainingMillis);
			} else {
				// heuristic;-) A match usually takes between 350s, a game has four matches
				dto.setEstimatedRemainingMillis(AVERAGE_GAME_LENGTH_MILLIS);
			}
		} else {
			dto.setEstimatedRemainingMillis(AVERAGE_GAME_LENGTH_MILLIS);
		}
		return dto;
	}

}
