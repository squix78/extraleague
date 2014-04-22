package ch.squix.extraleague.model.ranking.badge;

import java.io.Serializable;
import java.util.Date;

public class Badge implements Serializable {
	
	private String name;
	private Date earnedDate;
	
	public Badge() {
		
	}
	
	public Badge(String name, Date earnedDate) {
		this.name = name;
		this.earnedDate = earnedDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getEarnedDate() {
		return earnedDate;
	}
	public void setEarnedDate(Date earnedDate) {
		this.earnedDate = earnedDate;
	}
	
	

}
