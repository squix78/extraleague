package ch.squix.extraleague.rest.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;

public class SummaryService {
	

	public static SummaryDto getSummaryDto(Game game, List<Match> matches) {
		SummaryDto dto = new SummaryDto();
		Map<String, Integer> playerScores = new HashMap<>();
		Map<String, Integer> playerGoals = new HashMap<>();
		Map<String, Integer> playerEloPoints = new HashMap<>();
		
		for (String player : game.getPlayers()) {
			playerScores.put(player, 0);
			playerGoals.put(player, 0);
			playerEloPoints.put(player, 0);
		}
		
		for (Match match : matches) {
			Integer teamAScore = match.getTeamAScore();
			Integer teamBScore = match.getTeamBScore();
			Integer maxScore = Math.max(teamAScore, teamBScore);
			if (maxScore == match.getMaxGoals()) {
				String [] winners = null;
				String [] losers = null;
				Integer winPoints = 0;
				if (teamAScore > teamBScore) {
					winners = match.getTeamA();
					losers = match.getTeamB();
					winPoints = match.getWinPointsTeamA();
				} else {
					winners = match.getTeamB();
					losers = match.getTeamA();
					winPoints = match.getWinPointsTeamB();
				}
				for (String winner : winners) {
					Integer score = playerScores.get(winner);
					score++;
					playerScores.put(winner, score);
					Integer eloPoints = playerEloPoints.get(winner);
					playerEloPoints.put(winner, eloPoints + winPoints);
				}
				for (String loser : losers) {
					Integer eloPoints = playerEloPoints.get(loser);
					playerEloPoints.put(loser, eloPoints - winPoints);
				}
				for (String player : playerScores.keySet()) {
					Integer goals = playerGoals.get(player);
					goals += Collections.frequency(match.getScorers(), player);
					playerGoals.put(player, goals);
				}
	
				List<GoalDto> goals = new ArrayList<>();
				List<String> teamAPlayers = Arrays.asList(match.getTeamA());
				int currentTeamAScore = 0;
				int currentTeamBScore = 0;
				for (String player : match.getScorers()) {
					GoalDto goal = new GoalDto();
					goal.setScorer(player);
					
					if (teamAPlayers.contains(player)) {
						goal.setScorerTeam("a");
						currentTeamAScore++;
					} else {
						goal.setScorerTeam("b");
						currentTeamBScore++;
					}
					goal.setTeamAScore(currentTeamAScore);
					goal.setTeamBScore(currentTeamBScore);
					goals.add(goal);
				}
				
				MatchSummaryDto matchSummary = new MatchSummaryDto();
				matchSummary.setTeamA(match.getTeamA());
				matchSummary.setTeamB(match.getTeamB());
				matchSummary.setTeamAScore(teamAScore);
				matchSummary.setTeamBScore(teamBScore);
				matchSummary.setGoals(goals);
				dto.getMatches().add(matchSummary);
			}
		}
		for (String player : game.getPlayers()) {
			dto.getPlayerScores().add(new PlayerScoreDto(
					player, playerScores.get(player), 
					playerGoals.get(player), 
					playerEloPoints.get(player)));
		}
		Date startDate = game.getStartDate();
		Date endDate = game.getEndDate();
		if (startDate != null && endDate != null) {
			Long durationInSeconds = (endDate.getTime() - startDate.getTime()) / 1000;
			dto.setGameDurationSeconds(durationInSeconds);
		}
		return dto;
	}

}
