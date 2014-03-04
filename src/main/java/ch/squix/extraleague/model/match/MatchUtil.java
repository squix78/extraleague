package ch.squix.extraleague.model.match;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MatchUtil {
    
	public static PlayerMatchResult getPlayerMatchResult(Match match, String player) {
		return getPlayerMatchMap(match).get(player);
	}
	
	public static Map<String, PlayerMatchResult> getPlayerMatchMap(Match match) {
        Map<String, PlayerMatchResult> results = new LinkedHashMap<>();
        boolean hasTeamAWon = match.getTeamAScore() > match.getTeamBScore();
        int index = 0;
        Position[] teamPositions = new Position[] {Position.Offensive, Position.Defensive};
        for (String player : match.getTeamA()) {
            PlayerMatchResult result = new PlayerMatchResult();
            result.setPlayer(player);
            result.setPartner(match.getTeamA()[(index + 1) % 2]);
            result.setOpponents(match.getTeamB());
            result.setGoalsMade(match.getTeamAScore());
            result.setGoalsGot(match.getTeamBScore());
            result.setHasWon(hasTeamAWon);
            result.setPosition(teamPositions[index]);
            results.put(player, result);
            index++;
        }
        index = 0;
        for (String player : match.getTeamB()) {
            PlayerMatchResult result = new PlayerMatchResult();
            result.setPlayer(player);
            result.setPartner(match.getTeamB()[(index + 1) % 2]);
            result.setOpponents(match.getTeamA());
            result.setGoalsMade(match.getTeamBScore());
            result.setGoalsGot(match.getTeamAScore());
            result.setPosition(teamPositions[index]);
            result.setHasWon(!hasTeamAWon);
            results.put(player, result);
            index++;
        }
        return results;
	}
	
    public static List<PlayerMatchResult> getPlayerMatchResults(Match match) {
    	return new ArrayList<PlayerMatchResult>(getPlayerMatchMap(match).values());
    }

}
