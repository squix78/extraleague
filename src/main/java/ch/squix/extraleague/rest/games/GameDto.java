package ch.squix.extraleague.rest.games;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameDto {

	private Long id;
	private String table;
	private List<String> players = new ArrayList<>();
	
	private Date startDate;
	private Date endDate;
	
	public Long getId() {
		return id;
	}

	public String getTable() {
		return table;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
}
