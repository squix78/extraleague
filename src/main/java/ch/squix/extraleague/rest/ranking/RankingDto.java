package ch.squix.extraleague.rest.ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Data;
import ch.squix.extraleague.rest.statistics.DataTuple;

@Data
public class RankingDto {

    private String player;
    private List<String> badges = new ArrayList<>();
    private Integer gamesWon;
    private Integer gamesLost;
    private Integer goalsMade;
    private Integer goalsGot;
    private Integer ranking;
    private Integer achievementPoints;

    private String bestPartner;
    private Double bestPartnerRate;
    private String worstPartner;
    private Double worstPartnerRate;
    private String bestOpponent;
    private Double bestOpponentRate;
    private String worstOpponent;
    private Double worstOpponentRate;

    private List<PlayerComboDto> partners = new ArrayList<>();
    private List<PlayerComboDto> opponents = new ArrayList<>();


    private Double currentShapeRate;

    private Double offensivePositionRate;
    private Double defensivePositionRate;

    private Double tightlyLostRate;
    private Double tightlyWonRate;

    private Long averageSecondsPerMatch;
    private Set<String> playedWith;
    private Set<String> neverPlayedWith;
    private Integer eloValue;
    private Integer eloRanking;
    
    private Double trueSkillRating;
    private Integer trueSkillRanking;
    private Double trueSkillMean;
    private Double trueSkillSigma;
    
    private Double averageGoalsPerMatch;
    private Integer maxGoalsPerGame;
    
    private List<DataTuple<Integer, Double>> scoreHistogram = new ArrayList<>();


    public Double getSuccessRate() {
        return 1.0 * gamesWon / (gamesWon + gamesLost);
    }


    public Double getGoalRate() {
        return 1.0 * goalsMade / (goalsMade + goalsGot);
    }

    public Double getGoalPlusMinus() {
        return 1.0 * (goalsMade - goalsGot) / getTotalGames();
    }

    public Integer getTotalGames() {
        return gamesLost + gamesWon;
    }


}
