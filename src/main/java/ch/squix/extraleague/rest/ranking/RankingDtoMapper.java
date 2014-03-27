package ch.squix.extraleague.rest.ranking;

import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

import java.util.ArrayList;
import java.util.List;

public class RankingDtoMapper {

    public static List<RankingDto> convertToDto(Ranking ranking) {
        List<RankingDto> playerRankingList = new ArrayList<>();
        List<PlayerRanking> playerRankings = ranking.getPlayerRankings();
        if (playerRankings != null) {
            for (PlayerRanking playerRanking : playerRankings) {
                RankingDto rankingDto = new RankingDto();
                rankingDto.setPlayer(playerRanking.getPlayer());
                rankingDto.setGamesWon(playerRanking.getGamesWon());
                rankingDto.setGamesLost(playerRanking.getGamesLost());
                rankingDto.setRanking(playerRanking.getRanking());
                rankingDto.getBadges().clear();
                rankingDto.getBadges().addAll(playerRanking.getBadges());
                rankingDto.setGoalsMade(playerRanking.getGoalsMade());
                rankingDto.setGoalsGot(playerRanking.getGoalsGot());
                rankingDto.setOffensivePositionRate(playerRanking.getOffensivePositionRate());
                rankingDto.setDefensivePositionRate(playerRanking.getDefensivePositionRate());
                rankingDto.setBestPartner(playerRanking.getBestPartner());
                rankingDto.setBestPartnerRate(playerRanking.getBestPartnerRate());
                rankingDto.setWorstPartner(playerRanking.getWorstPartner());
                rankingDto.setWorstOpponentRate(playerRanking.getWorstPartnerRate());
                rankingDto.setBestOpponent(playerRanking.getBestOpponent());
                rankingDto.setBestOpponentRate(playerRanking.getBestOpponentRate());
                rankingDto.setWorstOpponent(playerRanking.getWorstOpponent());
                rankingDto.setWorstOpponentRate(playerRanking.getWorstOpponentRate());
                rankingDto.setAverageSecondsPerMatch(playerRanking.getAverageSecondsPerMatch());
                rankingDto.setCurrentShapeRate(playerRanking.getCurrentShapeRate());
                rankingDto.setPlayedWith(playerRanking.getPlayedWith());
                rankingDto.setNeverPlayedWith(playerRanking.getNeverPlayedWith());
                rankingDto.setTightlyLostRate(playerRanking.getTightlyLostRate());
                rankingDto.setTightlyWonRate(playerRanking.getTightlyWonRate());
                rankingDto.setPartners(PlayerComboDtoMapper.mapToDtos(playerRanking.getPartners()));
                rankingDto.setOpponents(PlayerComboDtoMapper.mapToDtos(playerRanking.getOpponents()));
                rankingDto.setRankingPoints(playerRanking.getRankingPoints());
                rankingDto.setDynamicRanking(playerRanking.getDynamicRanking());
                rankingDto.setAverageGoalsPerMatch(playerRanking.getAverageGoalsPerMatch());
                playerRankingList.add(rankingDto);
            }
        }
        return playerRankingList;
    }
    
	public static RankingDto getPlayerRanking(String player, Ranking ranking) {
		List<RankingDto> rankings = convertToDto(ranking);
		for (RankingDto dto : rankings) {
			if (dto.getPlayer().equals(player)) {
				return dto;
			}
		}
		return null;
	}

}
