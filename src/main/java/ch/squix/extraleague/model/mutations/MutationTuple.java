package ch.squix.extraleague.model.mutations;

import java.io.Serializable;

public class MutationTuple implements Serializable {

	private static final long serialVersionUID = -4347563309408143937L;
	
	private String player;
	private String value;

	private String description;
	
	public MutationTuple() {
		
	}
	
	public MutationTuple(String player, String value, String description) {
		this.player = player;
		this.value = value;
		this.description = description;
	}
	
	public String getPlayer() {
		return player;
	}
	public String getValue() {
		return value;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
