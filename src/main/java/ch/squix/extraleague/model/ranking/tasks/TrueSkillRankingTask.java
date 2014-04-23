/*
 * Copyright (C) 2014 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of Netcetera AG, Switzerland.
 * The program(s) may be used and/or copied only with the written permission of Netcetera AG or
 * in accordance with the terms and conditions stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */
package ch.squix.extraleague.model.ranking.tasks;

import java.util.Collection;
import java.util.Map;

import jskills.GameInfo;
import jskills.IPlayer;
import jskills.ITeam;
import jskills.Player;
import jskills.Rating;
import jskills.SkillCalculator;
import jskills.Team;
import jskills.trueskill.TwoTeamTrueSkillCalculator;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;


public class TrueSkillRankingTask implements RankingTask {
  
  private SkillCalculator calculator = new TwoTeamTrueSkillCalculator();

  @Override
  public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    for (Map.Entry<String, PlayerRanking> entry : playerRankingMap.entrySet()) {
      entry.getValue().setTrueSkillMean(GameInfo.getDefaultGameInfo().getInitialMean());
      entry.getValue().setTrueSkillSigma(GameInfo.getDefaultGameInfo().getInitialStandardDeviation());
    }
    for (Match match : matches.getMatchesSortedByTime()) {
      IPlayer player1 = new Player<Integer>(1);
      PlayerRanking player1Ranking = playerRankingMap.get(match.getTeamA()[0]);
      Rating player1Rating = new Rating(player1Ranking.getTrueSkillMean(), player1Ranking.getTrueSkillSigma());
      
      IPlayer player2 = new Player<Integer>(2);
      PlayerRanking player2Ranking = playerRankingMap.get(match.getTeamA()[1]);
      Rating player2Rating = new Rating(player2Ranking.getTrueSkillMean(), player2Ranking.getTrueSkillSigma());
      
      IPlayer player3 = new Player<Integer>(3);
      PlayerRanking player3Ranking = playerRankingMap.get(match.getTeamB()[0]);
      Rating player3Rating = new Rating(player3Ranking.getTrueSkillMean(), player3Ranking.getTrueSkillSigma());
      
      IPlayer player4 = new Player<Integer>(4);
      PlayerRanking player4Ranking = playerRankingMap.get(match.getTeamB()[1]);
      Rating player4Rating = new Rating(player4Ranking.getTrueSkillMean(), player4Ranking.getTrueSkillSigma());
  
      GameInfo gameInfo = GameInfo.getDefaultGameInfo();
  
      Team team1 = new Team().addPlayer(player1, player1Rating).addPlayer(player2, player2Rating);
      Team team2 = new Team().addPlayer(player3, player3Rating).addPlayer(player4, player4Rating);
  
      Collection<ITeam> teams = Team.concat(team1, team2);

      int[] matchRanking = MatchUtil.hasTeamAWon(match) ? new int[] {1, 2} : new int[] {2, 1};
      Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teams, matchRanking);

      Rating player1NewRating = newRatings.get(player1);
      Rating player2NewRating = newRatings.get(player2);
      Rating player3NewRating = newRatings.get(player3);
      Rating player4NewRating = newRatings.get(player4);

      player1Ranking.setTrueSkillMean(player1NewRating.getMean());
      player1Ranking.setTrueSkillSigma(player1NewRating.getStandardDeviation());
      player1Ranking.setTrueSkillRating(player1NewRating.getConservativeRating());
      
      player2Ranking.setTrueSkillMean(player2NewRating.getMean());
      player2Ranking.setTrueSkillSigma(player2NewRating.getStandardDeviation());
      player2Ranking.setTrueSkillRating(player2NewRating.getConservativeRating());
      
      player3Ranking.setTrueSkillMean(player3NewRating.getMean());
      player3Ranking.setTrueSkillSigma(player3NewRating.getStandardDeviation());
      player3Ranking.setTrueSkillRating(player3NewRating.getConservativeRating());
      
      player4Ranking.setTrueSkillMean(player4NewRating.getMean());
      player4Ranking.setTrueSkillSigma(player4NewRating.getStandardDeviation());
      player4Ranking.setTrueSkillRating(player4NewRating.getConservativeRating());
    }
  }

}
