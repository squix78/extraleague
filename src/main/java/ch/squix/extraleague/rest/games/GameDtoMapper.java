package ch.squix.extraleague.rest.games;

import java.util.Date;

import ch.squix.extraleague.model.game.Game;

public class GameDtoMapper {
	
	private static final Long AVERAGE_MILLIS_BETWEEN_GOALS = 70 * 1000L;

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
		dto.setIsGameFinished(game.getIsGameFinished());
		Integer maxMatches = game.getMaxMatches();
		if (maxMatches == null) {
			maxMatches = 4;
		}
		Integer maxGoals = game.getMaxGoals();
		if (maxGoals == null) {
			maxGoals = 5;
		}
		dto.setMaxMatches(maxMatches);
		dto.setMaxGoals(maxGoals);
		dto.setGameMode(game.getGameMode());
		if (game.getFirstGoalDate() != null && game.getEndDate() == null) {
			Double progress = game.getGameProgress();
			if (progress == null) {
				progress = 0d;
			}
			Long now = new Date().getTime();
			Long durationSinceFirstGoal =  now - game.getFirstGoalDate().getTime();
			// only calculate
			if (durationSinceFirstGoal > 10000 && progress >= 0.1 ) {
				Double goalTimeShare = 1.0d / (maxMatches * maxGoals);
				Long estimatedRemainingMillis = Math.round((1 - progress) * durationSinceFirstGoal / (progress - goalTimeShare));
				dto.setEstimatedRemainingMillis(estimatedRemainingMillis);
			} else {
				// heuristic;-) A match usually takes between 350s
				dto.setEstimatedRemainingMillis(AVERAGE_MILLIS_BETWEEN_GOALS * maxMatches * maxGoals);
			}
		} else {
			dto.setEstimatedRemainingMillis(AVERAGE_MILLIS_BETWEEN_GOALS * maxMatches * maxGoals);
		}
		return dto;
	}

}
