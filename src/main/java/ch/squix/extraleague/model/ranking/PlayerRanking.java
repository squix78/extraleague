package ch.squix.extraleague.model.ranking;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;


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

}
