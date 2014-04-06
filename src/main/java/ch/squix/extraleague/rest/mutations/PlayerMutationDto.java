package ch.squix.extraleague.rest.mutations;

import java.util.Date;

public class PlayerMutationDto {

	private String player;
	private String value;
	private String text;
	private Date createdDate;

	public String getValue() {
		return value;
	}
	public String getText() {
		return text;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getPlayer() {
		return player;
	}

}
