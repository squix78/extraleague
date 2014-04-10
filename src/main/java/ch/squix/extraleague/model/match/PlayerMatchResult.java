package ch.squix.extraleague.model.match;


public class PlayerMatchResult {

    private boolean hasWon = false;
    private Integer goalsMade = 0;
    private Integer goalsGot = 0;
    private String player = "";
    private String partner = "";
    private String [] opponents = {};
    private Position position;
	private Integer playerGoals;
	private Boolean hasPlayerGoals = false;
    
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

	/**
	 * @return the partner
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * @param partner the partner to set
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}

	/**
	 * @return the opponents
	 */
	public String [] getOpponents() {
		return opponents;
	}

	/**
	 * @param opponents the opponents to set
	 */
	public void setOpponents(String [] opponents) {
		this.opponents = opponents;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	public void setPlayerGoals(Integer playerGoals) {
		this.playerGoals = playerGoals;
	}
	
	public Integer getPlayerGoals() {
		return playerGoals;
	}

	public Boolean hasPlayerGoals() {
		return hasPlayerGoals;
	}

	public void setHasPlayerGoals(Boolean hasPlayerGoals) {
		this.hasPlayerGoals = hasPlayerGoals;
	}
	
	public String getMatchResultAsPlayersView() {
		return goalsMade + ":" + goalsGot;
	}
}
