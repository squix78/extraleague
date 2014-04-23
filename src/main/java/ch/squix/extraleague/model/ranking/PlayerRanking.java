package ch.squix.extraleague.model.ranking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ch.squix.extraleague.model.match.PlayerCombo;
import ch.squix.extraleague.rest.statistics.DataTuple;


public class PlayerRanking implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String player;
    private Integer gamesWon = 0;
    private Integer gamesLost = 0;
    private Integer ranking;
    private Set<String> badges = new TreeSet<>();
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

    private List<PlayerCombo> partners = new ArrayList<>();
    private List<PlayerCombo> opponents = new ArrayList<>();

    private Double offensivePositionRate;
    private Double defensivePositionRate;

    private Long averageSecondsPerMatch;

    private Double currentShapeRate;

    private Double tightlyLostRate;
    private Double tightlyWonRate;

    private Set<String> playedWith = new HashSet<>();
    private Set<String> neverPlayedWith = new HashSet<>();
    private Integer rankingPoints = 0;
    private Integer dynamicRanking;

	private Double averageGoalsPerMatch;
	
	private Integer eloValue;
	private Integer eloRanking;

  private Double trueSkillsMean;
  private Double trueSkillsSigma;
  private Double trueSkillsRating;
  private Integer trueSkillsRanking;

	private List<DataTuple<Integer, Double>> scoreHistogram = new ArrayList<>();

	private Integer maxGoalsPerGame = 0;

    public String getPlayer() {
        return player;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public Integer getGamesLost() {
        return gamesLost;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setGamesLost(Integer gamesLost) {
        this.gamesLost = gamesLost;
    }

    public Integer getTotalGames() {
        return gamesWon + gamesLost;
    }

    /**
     * @return the badges
     */
    public Set<String> getBadges() {
        return badges;
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

    /**
     * @return the ranking
     */
    public Integer getRanking() {
        return ranking;
    }

    /**
     * @param ranking the ranking to set
     */
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getGoalsMade() {
        return goalsMade;
    }

    public void setGoalsMade(Integer goalsMade) {
        this.goalsMade = goalsMade;
    }

    public Integer getGoalsGot() {
        return goalsGot;
    }

    public void setGoalsGot(Integer goalsGot) {
        this.goalsGot = goalsGot;
    }

    public String getBestPartner() {
        return bestPartner;
    }

    public String getWorstPartner() {
        return worstPartner;
    }

    public String getBestOpponent() {
        return bestOpponent;
    }

    public String getWorstOpponent() {
        return worstOpponent;
    }

    public void setBestPartner(String bestPartner) {
        this.bestPartner = bestPartner;
    }

    public void setWorstPartner(String worstPartner) {
        this.worstPartner = worstPartner;
    }

    public void setBestOpponent(String bestOpponent) {
        this.bestOpponent = bestOpponent;
    }

    public void setWorstOpponent(String worstOpponent) {
        this.worstOpponent = worstOpponent;
    }

    public Double getBestPartnerRate() {
        return bestPartnerRate;
    }

    public Double getWorstPartnerRate() {
        return worstPartnerRate;
    }

    public Double getBestOpponentRate() {
        return bestOpponentRate;
    }

    public Double getWorstOpponentRate() {
        return worstOpponentRate;
    }

    public void setBestPartnerRate(Double bestPartnerRate) {
        this.bestPartnerRate = bestPartnerRate;
    }

    public void setWorstPartnerRate(Double worstPartnerRate) {
        this.worstPartnerRate = worstPartnerRate;
    }

    public void setBestOpponentRate(Double bestOpponentRate) {
        this.bestOpponentRate = bestOpponentRate;
    }

    public void setWorstOpponentRate(Double worstOpponentRate) {
        this.worstOpponentRate = worstOpponentRate;
    }

    public Double getOffensivePositionRate() {
        return offensivePositionRate;
    }

    public Double getDefensivePositionRate() {
        return defensivePositionRate;
    }

    public void setOffensivePositionRate(Double offensivePositionRate) {
        this.offensivePositionRate = offensivePositionRate;
    }

    public void setDefensivePositionRate(Double defensivePositionRate) {
        this.defensivePositionRate = defensivePositionRate;
    }

    public void setAverageSecondsPerMatch(long averageSecondsPerMatch) {
        this.averageSecondsPerMatch = averageSecondsPerMatch;
    }

    public Long getAverageSecondsPerMatch() {
        return averageSecondsPerMatch;
    }

    public void setCurrentShapeRate(Double currentShapeRate) {
        this.currentShapeRate = currentShapeRate;
    }

    public Double getCurrentShapeRate() {
        return currentShapeRate;
    }

    public Set<String> getPlayedWith() {
        return playedWith;
    }

    public Set<String> getNeverPlayedWith() {
        return neverPlayedWith;
    }

    public void setPlayedWith(Set<String> playedWith) {
        this.playedWith = playedWith;
    }

    public void setNeverPlayedWith(Set<String> neverPlayedWith) {
        this.neverPlayedWith = neverPlayedWith;
    }

    /**
     * @return the tightlyLostRate
     */
    public Double getTightlyLostRate() {
        return tightlyLostRate;
    }

    /**
     * @param tightlyLostRate the tightlyLostRate to set
     */
    public void setTightlyLostRate(Double tightlyLostRate) {
        this.tightlyLostRate = tightlyLostRate;
    }

    /**
     * @return the tightlyWonRate
     */
    public Double getTightlyWonRate() {
        return tightlyWonRate;
    }

    /**
     * @param tightlyWonRate the tightlyWonRate to set
     */
    public void setTightlyWonRate(Double tightlyWonRate) {
        this.tightlyWonRate = tightlyWonRate;
    }

    /**
     * @return the partners
     */
    public List<PlayerCombo> getPartners() {
        return partners;
    }

    /**
     * @param partners the partners to set
     */
    public void setPartners(List<PlayerCombo> partners) {
        this.partners = partners;
    }

    /**
     * @return the opponents
     */
    public List<PlayerCombo> getOpponents() {
        return opponents;
    }

    /**
     * @param opponents the opponents to set
     */
    public void setOpponents(List<PlayerCombo> opponents) {
        this.opponents = opponents;
    }

    public Integer getRankingPoints() {
        return rankingPoints;
    }

    public void setRankingPoints(Integer rankingPoints) {
        this.rankingPoints = rankingPoints;
    }

    public void setDynamicRanking(int dynamicRanking) {
        this.dynamicRanking = dynamicRanking;
    }

    public Integer getDynamicRanking() {
        return dynamicRanking;
    }

	public void setAverageGoalsPerMatch(Double averageGoalsPerMatch) {
		this.averageGoalsPerMatch = averageGoalsPerMatch;
	}
	
	public Double getAverageGoalsPerMatch() {
		return averageGoalsPerMatch;
	}

	public Integer getEloValue() {
		return eloValue;
	}

	public void setEloValue(Integer eloValue) {
		this.eloValue = eloValue;
	}

	public Integer getEloRanking() {
		return eloRanking;
	}

	public void setEloRanking(Integer eloRanking) {
		this.eloRanking = eloRanking;
	}
	
  public Double getTrueSkillsMean() {
    return trueSkillsMean;
  }
  
  public void setTrueSkillsMean(Double trueSkillsMean) {
    this.trueSkillsMean = trueSkillsMean;
  }
  
  public Double getTrueSkillsSigma() {
    return trueSkillsSigma;
  }
  
  public void setTrueSkillsSigma(Double trueSkillsSigma) {
    this.trueSkillsSigma = trueSkillsSigma;
  }
  
  public Double getTrueSkillsRating() {
    return trueSkillsRating;
  }
  
  public void setTrueSkillsRating(Double trueSkillsRating) {
    this.trueSkillsRating = trueSkillsRating;
  }

  public Integer getTrueSkillsRanking() {
    return trueSkillsRanking;
  }
  
  public void setTrueSkillsRanking(Integer trueSkillsRanking) {
    this.trueSkillsRanking = trueSkillsRanking;
  }

  public void setScoreHistogram(List<DataTuple<Integer, Double>> scoreHistogram) {
		this.scoreHistogram = scoreHistogram;
	}
	
	public List<DataTuple<Integer, Double>> getScoreHistogram() {
		return scoreHistogram;
	}

	public Integer getMaxGoalsPerGame() {
		return maxGoalsPerGame;
	}
	
	public void setMaxGoalsPerGame(Integer maxGoalsPerGame) {
		this.maxGoalsPerGame = maxGoalsPerGame;
	}
}
