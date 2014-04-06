package ch.squix.extraleague.model.mutations;

import java.io.Serializable;
import java.util.Date;

public class PlayerMutation implements Serializable {

	private static final long serialVersionUID = -4347563309408143937L;
	
	private String player;
	private String value;

	private String description;
	private Date createdDate;
	
	public PlayerMutation() {
		
	}
	
	public PlayerMutation(String player, String value, String description) {
		this.player = player;
		this.value = value;
		this.description = description;
		this.createdDate = new Date();
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
