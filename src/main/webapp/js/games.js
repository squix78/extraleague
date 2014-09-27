angular.module('Games', ['gaeChannelService'])
  .factory('Games', ['$resource', function($resource) {
    return $resource('/rest/games');
  }])
  .factory('OpenGames', ['$resource', function($resource) {
    return $resource('/rest/openGames');
  }])
  .factory('PlayedGames', ['$resource', function($resource) {
    return $resource('/rest/playedGames');
  }])
  .factory('Game', ['$resource', function($resource) {
    return $resource('/rest/games/:gameId');
  }])
  .factory('Match', ['$resource', function($resource) {
      return $resource('/rest/games/:gameId/matches');
    }])
  .factory('GameService', ['$rootScope', 'OpenGames', 'Game', 'Match', 'NotificationService', function($rootScope, OpenGames, Game, Match, NotificationService) {
	
	var games = {};
	games.gameList = [];
	games.openGamesList = [];
	games.gameMap = {};
	games.currentGame = {};
	games.currentGameId = undefined;
	games.currentMatchIndex = undefined;
	games.currentMatch = undefined;
	games.currentMaxMatches = undefined;
	games.currentMaxGoals = undefined;
	
	var service = {
		setGames: function(newGames) {
			var me = this;
			games.gameList.length = 0;
			games.gameMap = {};
			var index = 0;
			angular.forEach(newGames, function(game, key) {
				game.matches = Match.query({gameId: game.id}, function() {
					service.updateCurrentGame();
				});
				games.gameList.push(game);
				games.gameMap[game.id] = game;
				if (!game.isGameFinished) {
					games.openGamesList.push(game);
				}
				console.log("Adding game with id " + game.id);
				index++;
			});
			me.updateCurrentGame();
			me.calculateRemainingMillis();
			if(!$rootScope.$$phase) {
				$rootScope.$apply();
			}
			
		},
		updateCurrentGame: function() {
			if (angular.isDefined(games.currentGameId)) {
				var newCurrentGame = games.gameMap[games.currentGameId];
				if (angular.isDefined(newCurrentGame)) {
					games.currentGame = newCurrentGame;
					games.currentMaxGoals = newCurrentGame.maxGoals;
					games.currentMaxMatches = newCurrentGame.maxMatches;
					service.updateCurrentMatch();
				} else {
					games.currentGame = Game.get({gameId: games.currentGameId}, function() {
						games.currentGame.matches = Match.query({gameId: games.currentGame.id}, function() {
							service.updateCurrentMatch();
						});
					});
				}

			} 
		},
		updateCurrentMatch: function() {
			if (angular.isDefined(games.currentGame)) {
				var maxMatches = games.currentGame.maxMatches;
				if (!angular.isDefined(games.currentMatchIndex)) {
					games.currentMatchIndex = Math.min(games.currentGame.numberOfCompletedGames, maxMatches - 1);
				}
				games.currentMatch = games.currentGame.matches[games.currentMatchIndex];
			}
		},
		loadOpenGames: function() {
			var me = this;
			var loadedOpenGames = OpenGames.query({});
			loadedOpenGames.$promise.then(function(loadedGames) {
				me.setGames(loadedGames);
			});
			return games;
			
		},
		getOpenGames: function() {
			return games;
		},
		deleteGame: function(game) {
		  var me = this;
	      Game.remove({gameId: game.id}, function() {
	    	  if (game.id === games.currentGameId) {
	    		  games.currentGameId = undefined;
	    	  }
	          me.loadOpenGames();
	      })
		},
		
		calculateRemainingMillis: function() {
		  var tableSums = {};
		  for (i = 0; i < games.gameList.length; i++) {
			  var currentGame = games.gameList[i];
			  var currentTableSum = (tableSums[currentGame.table] | 0);
			  tableSums[currentGame.table] = currentGame.estimatedRemainingMillis + currentTableSum;
			  currentGame.summedRemainingMilis = tableSums[currentGame.table];
		  }
		},
		setCurrentGame: function(gameId) {
			games.currentGameId = gameId;
			games.currentMatchIndex = undefined;
		},
		addPlayerScore: function(player) {
			
			if (angular.isDefined(games.currentGame)) {
				var maxGoals = games.currentGame.maxGoals;
				if (games.currentMatch.teamAScore < maxGoals
					&& games.currentMatch.teamBScore < maxGoals) {

					games.currentMatch.scorers.push(player);
					var goal = {scorer: player, time: new Date()};
					games.currentMatch.goals.push(goal);
					service.calculateScores(games.currentMatch);
					service.saveCurrentMatch();
					//service.checkEndOfMatch();
				}
			}
		},
		removeLastGoal: function() {
			if (games.currentMatch.scorers.length > 0) {
				var removedScorer = games.currentMatch.scorers.pop();
				games.currentMatch.goals.pop();
				games.isGameFinished = false;
				console.log("Removed scorer: " + removedScorer);
				service.calculateScores(games.currentMatch);
				service.saveCurrentMatch();
			}
		},
		calculateScores: function(match) {
			match.teamAScore = 0;
			match.teamBScore = 0;
			angular.forEach(match.scorers, function(scorer, key) {
				if (match.teamA.indexOf(scorer) > -1) {
					match.teamAScore++;
				} else if (match.teamB.indexOf(scorer) > -1) {
					match.teamBScore++;
				}
			});
			console.log("Calculated score: " + match.teamAScore + ":" + match.teamBScore);
		},
		saveCurrentMatch: function() {
		    games.isMatchSaving = true;
		    games.currentMatch.lastUpdate = new Date();
		    games.currentMatch.$save({gameId: games.currentGame.id}, function(match) {
		      games.isMatchSaving = false;
		      games.isErrorDialogVisible = false;
		      service.checkEndOfMatch();
		    }, function(error) {
		    	games.isMatchSaving = false;
		    	games.isErrorDialogVisible = true;
		    	games.errorStatus = error.status;
		    });
		},
		checkEndOfMatch: function() {
		    if (angular.isDefined(games.currentMatch)) {
		      var maxGoals = games.currentMatch.maxGoals;
		      var maxMatches = games.currentMatch.maxMatches;
		      if ((games.currentMatch.teamAScore >= maxGoals || games.currentMatch.teamBScore >=maxGoals)) {
			      games.currentMatch.endDate = new Date();
			      if (games.currentMatchIndex < maxMatches - 1) {  
			    	games.currentMatchIndex++;
			    	service.updateCurrentMatch();
			      } 
		      }
		      service.checkEndOfGame();
		    } 
		 },
		 checkEndOfGame: function() {
			 var currentGame = games.currentGame;
			 if (angular.isDefined(currentGame)) {
				 var maxGoals = currentGame.maxGoals;
				 var maxMatches = currentGame.maxMatches;

				 var finishedMatchCounter = 0;
				 angular.forEach(currentGame.matches, function(match, index) {
					 if ((match.teamAScore >= maxGoals || match.teamBScore >= maxGoals)) {
						 finishedMatchCounter++;
					 }
				 });
				 if (finishedMatchCounter === maxMatches) {
					 games.isGameFinished = true;
					 service.updateCurrentGame();
				 }
			 }
		 },
		 moveMatchIndexBy: function(increment) {
			  if (angular.isDefined(games.currentGame)) {
				  var maxMatches = games.currentGame.maxMatches;
				  var newMatchIndex = games.currentMatchIndex + increment;
				  if (newMatchIndex >=0 && newMatchIndex < maxMatches) {
					  games.currentMatchIndex = newMatchIndex;
					  service.updateCurrentGame();
				  }
			  }
		 },
		 hideAlert: function() {
			 games.isErrorDialogVisible = false;
		 }

	    
	 	
	};
	// Update the list, if a state changed on the server
	$rootScope.$on("UpdateOpenGames", function(event, message) {
	   console.log("Received change in open games from server");
	   service.setGames(message.openGames);
	});
	$rootScope.$on("GameFinished", function(event, message) {
		console.log("Received message that game is finished from server. Checking if affecting current");
		var finishedGame = message.finishedGame;
		if (games.currentGameId == finishedGame.id) {
			console.log("Current game is finished...");
			games.isGameFinished = true;
			if(!$rootScope.$$phase) {
				$rootScope.$apply();
			}
		}
	});
	$rootScope.$on("UpdateMatch", function(event, message) {
	    console.log("Received match update from server");
	    var updatedGame = message.game;
	    var updatedMatch = new Match(message.match);
	    if (!angular.isDefined(games.gameMap[updatedGame.id])) {
	    	console.log("Game to update does not exist here anymore");
	    	//games.isGameFinished = true;
			if(!$rootScope.$$phase) {
				$rootScope.$apply();
			}
	    	return;
	    }
	    for (i = 0; i < games.gameList.length; i++) {
	    	if (games.gameList[i].id===updatedGame.id) {
	    		updatedGame.matches = games.gameList[i].matches;
	    		games.gameList[i] = updatedGame;
	    		var matchUpdateCandidate = games.gameList[i].matches[updatedMatch.matchIndex];
	    		if (matchUpdateCandidate.lastUpdate <= updatedMatch.lastUpdate) {
	    			console.log("Match update is applicable.");
	    			games.gameList[i].matches[updatedMatch.matchIndex] = updatedMatch;
	    			service.updateCurrentGame();
	    			service.checkEndOfMatch();
	    		} else {
	    			console.log("Match update is outdated. Ignoring it. localTimeStamp: " 
	    					+ matchUpdateCandidate.lastUpdate + ", received: " + updatedMatch.lastUpdate);
	    		}
	    		service.calculateRemainingMillis();
				if(!$rootScope.$$phase) {
					$rootScope.$apply();
				}
	    	}
	    }
	});
	return service;
	}]);