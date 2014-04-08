package ch.squix.extraleague.model.ranking;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
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
	
	@Ignore
	private Map<String, PlayerRanking> playerRankingMap = new HashMap<>();

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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
	
	public PlayerRanking getPlayerRanking(String player) {
	    if (playerRankingMap.size() != playerRankings.size()) {
	        for (PlayerRanking playerRanking : playerRankings) {
	            playerRankingMap.put(playerRanking.getPlayer(), playerRanking);
	        }
	    }
	    return playerRankingMap.get(player);
	}


}
