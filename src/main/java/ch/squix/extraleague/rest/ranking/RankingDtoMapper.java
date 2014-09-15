package ch.squix.extraleague.rest.ranking;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.ranking.EternalRanking;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

public class RankingDtoMapper {

	private static List<RankingDto> getRankingDtos(List<PlayerRanking> playerRankings) {
		List<RankingDto> playerRankingList = new ArrayList<>();
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
				rankingDto.setWorstPartnerRate(playerRanking.getWorstPartnerRate());
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
				rankingDto.setEloValue(playerRanking.getEloValue());
				rankingDto.setAverageGoalsPerMatch(playerRanking.getAverageGoalsPerMatch());
				rankingDto.setEloRanking(playerRanking.getEloRanking());
				rankingDto.setScoreHistogram(playerRanking.getScoreHistogram());
				rankingDto.setTrueSkillRating(playerRanking.getTrueSkillRating());
				rankingDto.setTrueSkillRanking(playerRanking.getTrueSkillRanking());
				rankingDto.setTrueSkillMean(playerRanking.getTrueSkillMean());
				rankingDto.setTrueSkillSigma(playerRanking.getTrueSkillSigma());
				rankingDto.setMaxGoalsPerGame(playerRanking.getMaxGoalsPerGame());
				rankingDto.setAchievementPoints(playerRanking.getAchievementPoints());
				rankingDto.setBestSlam(playerRanking.getBestSlam());
				playerRankingList.add(rankingDto);
			}
		}
		return playerRankingList;
	}

	public static List<RankingDto> convertToDto(EternalRanking ranking) {
		return getRankingDtos(ranking.getPlayerRankings());
	}

	public static List<RankingDto> convertToDto(Ranking ranking) {
		if (ranking == null || ranking.getPlayerRankings() == null) {
			return new ArrayList<>();
		}
		return getRankingDtos(ranking.getPlayerRankings());
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
	
	public static RankingDto getPlayerRanking(String player, EternalRanking ranking) {
		List<RankingDto> rankings = convertToDto(ranking);
		for (RankingDto dto : rankings) {
			if (dto.getPlayer().equals(player)) {
				return dto;
			}
		}
		return null;
	}

}
