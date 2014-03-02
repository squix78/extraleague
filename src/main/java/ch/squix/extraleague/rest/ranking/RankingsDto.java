package ch.squix.extraleague.rest.ranking;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RankingsDto {
	
	private Map<String, RankingDto> rankingMap = new HashMap<>();
	private Date createdDate;

	/**
	 * @return the rankingMap
	 */
	public Map<String, RankingDto> getRankingMap() {
		return rankingMap;
	}

	/**
	 * @param rankingMap the rankingMap to set
	 */
	public void setRankingMap(Map<String, RankingDto> rankingMap) {
		this.rankingMap = rankingMap;
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

}
