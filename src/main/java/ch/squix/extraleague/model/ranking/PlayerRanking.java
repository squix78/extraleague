package ch.squix.extraleague.model.ranking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;
import ch.squix.extraleague.model.match.PlayerCombo;
import ch.squix.extraleague.rest.statistics.DataTuple;


@Data 
public class PlayerRanking implements Serializable {

    private static final long serialVersionUID = 1L;

    private String player;
    private Integer gamesWon = 0;
    private Integer gamesLost = 0;
    private Integer ranking;
    private Set<String> badges = new TreeSet<>();
    private Integer achievementPoints = 0;
    private Integer goalsMade = 0;
    private Integer goalsGot = 0;

    private String bestPartner;
    private Double bestPartnerRate;
    private String worstPartner;
    private Double worstPartnerRate;
    private String bestOpponent;
    private Double bestOpponentRate;
    private String worstOpponent;
    private Double worstOpponentRate;
    
    private Integer bestSlam;

    private List<PlayerCombo> partners = new ArrayList<>();
    private List<PlayerCombo> opponents = new ArrayList<>();

    private Double offensivePositionRate;
    private Double defensivePositionRate;

    private Long averageSecondsPerMatch;
    
    private Long secondsPlayedToday;

    private Double currentShapeRate;

    private Double tightlyLostRate;
    private Double tightlyWonRate;

    private Set<String> playedWith = new HashSet<>();
    private Set<String> neverPlayedWith = new HashSet<>();

    private Double averageGoalsPerMatch;

    private Integer eloValue;
    private Integer eloRanking;
    
    private Integer rankingDelta;
    private Integer eloDelta;

    private Double trueSkillMean;
    private Double trueSkillSigma;
    private Double trueSkillRating;
    private Integer trueSkillRanking;

    private List<DataTuple<Integer, Double>> scoreHistogram = new ArrayList<>();

    private Integer maxGoalsPerGame = 0;

    public Integer getTotalGames() {
        return gamesWon + gamesLost;
    }

    public Double getSuccessRate() {
        return 1.0 * gamesWon / (gamesWon + gamesLost);
    }

    public Double getGoalRate() {
        return 1.0 * goalsMade / (goalsMade + goalsGot);
    }

    public Double getGoalPlusMinus() {
        return 1.0 * (goalsMade - goalsGot) / getTotalGames();
    }

    public void increaseGamesWon() {
        gamesWon++;
    }

    public void increaseGamesLost() {
        gamesLost++;
    }
    
    public void addAchievementPoints(Integer points) {
    	achievementPoints += points;
    }

}
