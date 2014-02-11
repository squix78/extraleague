package ch.squix.extraleague.rest.ranking;

import java.util.HashMap;
import java.util.Map;


public class RankingsDto {
	
	private Map<String, RankingDto> rankingMap = new HashMap<>();

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

}
