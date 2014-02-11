package ch.squix.extraleague.model.ranking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

@Entity
@Cache
public class Ranking {
	
	@Id
	private Long id;
	
	@Index
	private Date createdDate;
	
	@Serialize(zip=true)
	private List<PlayerRanking> playerRankings = new ArrayList<>();

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public List<PlayerRanking> getPlayerRankings() {
		return playerRankings;
	}

	public void setPlayerRankings(List<PlayerRanking> playerRankings) {
		this.playerRankings = playerRankings;
	}

}
