package ch.squix.extraleague.model.match;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PlayerMatchResult {

    private boolean won = false;
    private Integer goalsMade = 0;
    private Integer goalsGot = 0;
    private String player = "";
    private String partner = "";
    private String [] opponents = {};
    private Position position = Position.Omnivore;
	private Integer playerGoals = null;
	private Boolean hasPlayerGoals = false;
	private List<String> inBetweenScores = new ArrayList<>();

	
	public String getMatchResultAsPlayersView() {
		return goalsMade + ":" + goalsGot;
	}

}
