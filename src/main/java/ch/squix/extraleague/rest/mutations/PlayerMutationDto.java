package ch.squix.extraleague.rest.mutations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerMutationDto {

	private List<String> players;
	private String value;
	private List<String> descriptions = new ArrayList<>();
	private Date createdDate;

	public String getValue() {
		return value;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setPlayers(List<String> players) {
		this.players = players;
	}
	public List<String> getPlayers() {
		return players;
	}
	public List<String> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

}
