package ch.squix.extraleague.model.match;


public class PlayerMatchResult {

    private boolean hasWon = false;
    private Integer goalsMade = 0;
    private Integer goalsGot = 0;
    private String player = "";
    
    public boolean hasWon() {
        return hasWon;
    }
    
    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
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

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
