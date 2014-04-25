package ch.squix.extraleague.rest.ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ch.squix.extraleague.rest.statistics.DataTuple;

public class RankingDto {

    private String player;
    private List<String> badges = new ArrayList<>();
    private Integer gamesWon;
    private Integer gamesLost;
    private Integer goalsMade;
    private Integer goalsGot;
    private Integer ranking;

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
    private Integer rankingPoints;
    private Integer dynamicRanking;
    private Integer eloValue;
    private Integer eloRanking;
    
    private Double trueSkillRating;
    private Integer trueSkillRanking;
    private Double trueSkillMean;
    private Double trueSkillSigma;
    
    private Double averageGoalsPerMatch;
    private Integer maxGoalsPerGame;
    
    private List<DataTuple<Integer, Double>> scoreHistogram = new ArrayList<>();

    public String getPlayer() {
        return player;
    }

    public Integer getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(Integer gamesLost) {
        this.gamesLost = gamesLost;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void increaseGamesWon() {
        this.gamesWon++;
    }

    public void increaseGamesLost() {
        this.gamesLost++;
    }

    public Double getSuccessRate() {
        return 1.0 * gamesWon / (gamesWon + gamesLost);
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

    public List<String> getBadges() {
        return badges;
    }

    public Integer getGoalsMade() {
        return goalsMade;
    }

    public void setGoalsMade(Integer goalsMade) {
        this.goalsMade = goalsMade;
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

    /**
     * @return the averageSecondsPerMatch
     */
    public Long getAverageSecondsPerMatch() {
        return averageSecondsPerMatch;
    }

    /**
     * @param averageSecondsPerMatch the averageSecondsPerMatch to set
     */
    public void setAverageSecondsPerMatch(Long averageSecondsPerMatch) {
        this.averageSecondsPerMatch = averageSecondsPerMatch;
    }

    /**
     * @return the currentShapeRate
     */
    public Double getCurrentShapeRate() {
        return currentShapeRate;
    }

    /**
     * @param currentShapeRate the currentShapeRate to set
     */
    public void setCurrentShapeRate(Double currentShapeRate) {
        this.currentShapeRate = currentShapeRate;
    }

    public void setPlayedWith(Set<String> playedWith) {
        this.playedWith = playedWith;
    }

    public Set<String> getPlayedWith() {
        return playedWith;
    }

    public void setNeverPlayedWith(Set<String> neverPlayedWith) {
        this.neverPlayedWith = neverPlayedWith;
    }

    public Set<String> getNeverPlayedWith() {
        return neverPlayedWith;
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

    public List<PlayerComboDto> getPartners() {
        return partners;
    }

    public void setPartners(List<PlayerComboDto> partners) {
        this.partners = partners;
    }

    public List<PlayerComboDto> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<PlayerComboDto> opponents) {
        this.opponents = opponents;
    }

    public void setRankingPoints(Integer rankingPoints) {
        this.rankingPoints = rankingPoints;
    }

    public Integer getRankingPoints() {
        return rankingPoints;
    }

    public void setDynamicRanking(Integer dynamicRanking) {
        this.dynamicRanking = dynamicRanking;
    }

    public Integer getDynamicRanking() {
        return dynamicRanking;
    }

	public Double getAverageGoalsPerMatch() {
		return averageGoalsPerMatch;
	}

	public void setAverageGoalsPerMatch(Double averageGoalsPerMatch) {
		this.averageGoalsPerMatch = averageGoalsPerMatch;
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

	public Double getTrueSkillRating() {
		return trueSkillRating;
	}

	public void setTrueSkillRating(Double trueSkillRating) {
		this.trueSkillRating = trueSkillRating;
	}

	public Integer getTrueSkillRanking() {
		return trueSkillRanking;
	}

	public void setTrueSkillRanking(Integer trueSkillRanking) {
		this.trueSkillRanking = trueSkillRanking;
	}

	/**
	 * @return the scoreHistogram
	 */
	public List<DataTuple<Integer, Double>> getScoreHistogram() {
		return scoreHistogram;
	}

	/**
	 * @param scoreHistogram the scoreHistogram to set
	 */
	public void setScoreHistogram(List<DataTuple<Integer, Double>> scoreHistogram) {
		this.scoreHistogram = scoreHistogram;
	}

	public Integer getMaxGoalsPerGame() {
		return maxGoalsPerGame;
	}

	public void setMaxGoalsPerGame(Integer maxGoalsPerGame) {
		this.maxGoalsPerGame = maxGoalsPerGame;
	}

    public Double getTrueSkillMean() {
        return trueSkillMean;
    }

    public void setTrueSkillMean(Double trueSkillMean) {
        this.trueSkillMean = trueSkillMean;
    }

    public Double getTrueSkillSigma() {
        return trueSkillSigma;
    }

    public void setTrueSkillSigma(Double trueSkillSigma) {
        this.trueSkillSigma = trueSkillSigma;
    }

}
