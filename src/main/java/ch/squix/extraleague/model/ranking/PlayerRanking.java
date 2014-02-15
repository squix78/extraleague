package ch.squix.extraleague.model.ranking;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import ch.squix.extraleague.model.match.PlayerCombo;


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

    /**
     * @param badges the badges to set
     */
    public void setBadges(Set<String> badges) {
        this.badges = badges;
    }


    public Double getSuccessRate() {
        return 1.0 * gamesWon / (gamesWon + gamesLost);
    }
    
    public Double getGoalRate() {
        return 1.0 * goalsMade / (goalsMade + goalsGot);
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


}
