package ch.squix.extraleague.rest.matches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Goal;
import ch.squix.extraleague.model.match.Match;

public class MatchInfoService {
	
	public static MatchInfoDto getMatchInfoDto(Match match) {
		MatchInfoDto infoDto = new MatchInfoDto();
		
		Map<String, Integer> goalMap = new HashMap<>();
		Map<String, Double> shareMap = new HashMap<>();
		List<String> scorers = match.getScorers();
		for (String player : match.getPlayers()) {
			goalMap.put(player, Collections.frequency(scorers, player));
		}
		infoDto.setGoalMap(goalMap); 
		Integer totalGoals = scorers.size();
		for (String player : match.getPlayers()) {
			Integer playerGoals = goalMap.get(player);
			if (playerGoals == null) {
				playerGoals = 0;
			}
			Double playerShare = 0d;
			if (totalGoals != 0) {
				playerShare = 1.0 * playerGoals / totalGoals;
			}
			shareMap.put(player, playerShare);
		}
		infoDto.setShareMap(shareMap);
		infoDto.setTeamAGoals(getScorers(match.getStartDate(), Arrays.asList(match.getTeamA()), match.getGoals()));
		infoDto.setTeamBGoals(getScorers(match.getStartDate(), Arrays.asList(match.getTeamB()), match.getGoals()));
		infoDto.setEvents(getEvents(match.getStartDate(), Arrays.asList(match.getTeamA()), Arrays.asList(match.getTeamB()), match.getGoals()));
		
		return infoDto;
	}
	
	private static List<EventDto> getEvents(Date matchStartDate, List<String> teamA, List<String> teamB, List<Goal> goals) {
		List<EventDto> events = new ArrayList<>();
		String lastScorer = "";
		Integer goalsInARow = 1;
		Integer teamAScore = 0;
		Integer teamBScore = 0;
		for (Goal goal : goals) {
			if (lastScorer.equals(goal.getScorer())) {
				goalsInARow++;
			} else {
				lastScorer = goal.getScorer();
				goalsInARow = 1;
			}
			String team = "B";
			String position = "";
			String oppositeKeeper = "";
			if (teamA.contains(goal.getScorer())) {
				teamAScore++;
				team = "A";
				oppositeKeeper = teamB.get(1);
				if (teamA.indexOf(goal.getScorer()) == 0) {
					position = "offense";
				} else {
					position = "defense";
				}
			} else {
				teamBScore++;
				team = "B";
				oppositeKeeper = teamA.get(1);
				if (teamA.indexOf(goal.getScorer()) == 0) {
					position = "defsense";
				} else {
					position = "offense";
				}
			}
			String goalTime = String.valueOf((goal.getTime().getTime() - matchStartDate.getTime()) / (1000 * 60));
			String message = goal.getScorer() + " scored!"; 
			if (goalsInARow == 2) {
				message = goal.getScorer() + " hit twice in a row!";
			} else if (goalsInARow == 3) {
				message = goal.getScorer() + " is on a spree! 3 Goals in a row!";
			} else if (goalsInARow == 4) {
				message = "This is legendary! 4 in a row! Is " + oppositeKeeper + " sleeping in the goal?";
			} else if (goalsInARow == 5) {
				message = "Unbelievable: 5! " + oppositeKeeper + " is apparently injured!";
			} else if (goalsInARow == 6) {
				message = "Unstoppable: 6! This is a humiliation!";
			} else if (goalsInARow == 7) {
				message = "Unbelievable";
			}
			EventDto dto = new EventDto();
			dto.setPlayer(goal.getScorer());
			dto.setGoalTime(goalTime);
			dto.setMessage(message);
			dto.setGoalsInARow(goalsInARow);
			dto.setScore(teamAScore + ":" + teamBScore);
			dto.setTeam(team);
			events.add(dto);
		}
		Collections.reverse(events);
		return events;
	}

	private static List<GoalDto> getScorers(Date matchStartTime, List<String> team, List<Goal> goals) {
		List<GoalDto> goalDtos = new ArrayList<>();
		for (Goal goal : goals) {
			if (team.contains(goal.getScorer())) {
				Long minutesSinceMatchStart = (goal.getTime().getTime() - matchStartTime.getTime()) / (1000 * 60);
				GoalDto dto = new GoalDto();
				dto.setScorer(goal.getScorer());
				dto.setTime(goal.getTime());
				dto.setMinutesSinceMatchStart(minutesSinceMatchStart);
				goalDtos.add(dto);
			}
		}
		return goalDtos;
	}

}
