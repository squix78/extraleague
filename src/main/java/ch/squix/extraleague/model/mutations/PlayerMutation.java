package ch.squix.extraleague.model.mutations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Joiner;

public class PlayerMutation implements Serializable {

	private static final long serialVersionUID = -4347563309408143937L;
	
	private String player;

	private List<String> descriptions = new ArrayList<>();
	private Date createdDate;
	
	public PlayerMutation() {
		
	}
	
	public PlayerMutation(String player) {
		this.player = player;
		this.createdDate = new Date();
	}
	
	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}


	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public String toString() {
		Joiner joiner = Joiner.on("/").skipNulls();
		return player + "/" + joiner.join(descriptions);
	}


	
	
}
