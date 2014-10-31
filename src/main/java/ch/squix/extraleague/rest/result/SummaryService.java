package ch.squix.extraleague.rest.result;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.base.Joiner;

import ch.squix.extraleague.model.challenger.WinnerTeam;
import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.notification.UpdateWinnersMessage;
import ch.squix.extraleague.rest.ranking.UpdateRankingsResource;

public class SummaryService {
	
	private static final Logger log = Logger.getLogger(SummaryService.class.getName());
	
	public static void updateTableWinners(Game game, List<Match> matches) {
		SummaryDto dto = getSummaryDto(game, matches);
		WinnerTeam winnerTeam = ofy().load().type(WinnerTeam.class).filter("table = ", game.getTable()).first().now();
		if (winnerTeam == null) {
			log.info("We didn't have a winner team for table " + game.getTable() + " yet. Creating...");
			winnerTeam = new WinnerTeam();
			winnerTeam.setTable(game.getTable());
		}
		winnerTeam.setCreatedDate(new Date());
		winnerTeam.setGameMode(game.getGameMode());
		winnerTeam.setWinners(dto.getWinners());
		log.info("Setting winner team to: " + Joiner.on(", ").join(dto.getWinners()));
		ofy().save().entity(winnerTeam).now();
		NotificationService.sendMessage(new UpdateWinnersMessage(game.getTable()));
	}

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
		dto.setPlayers(game.getPlayers());
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
	
				List<MatchSummaryDetailDto> goals = new ArrayList<>();
				List<String> teamAPlayers = Arrays.asList(match.getTeamA());
				int currentTeamAScore = 0;
				int currentTeamBScore = 0;
				for (String player : match.getScorers()) {
					MatchSummaryDetailDto goal = new MatchSummaryDetailDto();
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
				matchSummary.setMatchIndex(match.getMatchIndex());
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
		Collections.sort(dto.getPlayerScores(), new PlayerScoreComparator());
		if (dto.getPlayerScores().size() > 2) {
			List<String> winners = new ArrayList<>();
			winners.add(dto.getPlayerScores().get(0).getPlayer());
			winners.add(dto.getPlayerScores().get(1).getPlayer());
			dto.setWinners(winners);
			
		}
		if (dto.getPlayerScores().size() == 4) {
			List<String> loosers = new ArrayList<>();
			loosers.add(dto.getPlayerScores().get(2).getPlayer());
			loosers.add(dto.getPlayerScores().get(3).getPlayer());
			dto.setLoosers(loosers);
		}
		
		Date startDate = game.getStartDate();
		Date endDate = game.getEndDate();
		if (startDate != null && endDate != null) {
			Long durationInSeconds = (endDate.getTime() - startDate.getTime()) / 1000;
			dto.setGameDurationSeconds(durationInSeconds);
		}
		dto.setTable(game.getTable());
		return dto;
	}
	
	public static class PlayerScoreComparator implements Comparator<PlayerScoreDto> {

		@Override
		public int compare(PlayerScoreDto score1, PlayerScoreDto score2) {
			return score2.getEarnedEloPoints().compareTo(score1.getEarnedEloPoints());
		}
		
	}

	public static void addSummaryMutation(Game game, List<Match> matches) {
		SummaryDto summaryDto = SummaryService.getSummaryDto(game, matches);
		
		PlayerMutation mutation = new PlayerMutation(summaryDto.getPlayers());
		mutation.getDescriptions().add("Game finished. Elo points earned/ lost: ");
		for (PlayerScoreDto dto : summaryDto.getPlayerScores()) {
			mutation.getDescriptions().add(dto.getPlayer() + ": " + dto.getEarnedEloPoints());
		}
		ofy().save().entity(mutation).now();
		
	}

}
