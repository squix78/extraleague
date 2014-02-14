package ch.squix.extraleague.rest.ranking;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

public class RankingDtoMapper {
	
	public static List<RankingDto> convertToDto(Ranking ranking) {
		List<RankingDto> playerRankingList= new ArrayList<>();
		for (PlayerRanking playerRanking : ranking.getPlayerRankings()) {
			RankingDto rankingDto = new RankingDto();
			rankingDto.setPlayer(playerRanking.getPlayer());
			rankingDto.setGamesWon(playerRanking.getGamesWon());
			rankingDto.setGamesLost(playerRanking.getGamesLost());
			rankingDto.setRanking(playerRanking.getRanking());
			rankingDto.getBadges().clear();
			rankingDto.getBadges().addAll(playerRanking.getBadges());
			rankingDto.setGoalsMade(playerRanking.getGoalsMade());
			rankingDto.setGoalsGot(playerRanking.getGoalsGot());
			playerRankingList.add(rankingDto);
		}
		return playerRankingList;
	}

}
