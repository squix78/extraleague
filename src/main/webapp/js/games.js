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
	
	var openGames = {};
	openGames.gameList = [];
	openGames.gameMap = {};
	openGames.currentGame = {};
	openGames.currentGameId = undefined;
	openGames.currentMatchIndex = undefined;
	openGames.currentMatch = undefined;
	openGames.currentMaxMatches = undefined;
	openGames.currentMaxGoals = undefined;
	
	var service = {
		setGames: function(games) {
			var me = this;
			openGames.gameList.length = 0;
			openGames.gameMap = {};
			var index = 0;
			angular.forEach(games, function(game, key) {
				game.matches = Match.query({gameId: game.id}, function() {
					service.updateCurrentGame();
				});
				openGames.gameList.push(game);
				openGames.gameMap[game.id] = game;
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
			if (angular.isDefined(openGames.currentGameId)) {
				var newCurrentGame = openGames.gameMap[openGames.currentGameId];
				if (angular.isDefined(newCurrentGame)) {
					openGames.currentGame = newCurrentGame;
					openGames.currentMaxGoals = newCurrentGame.maxGoals;
					openGames.currentMaxMatches = newCurrentGame.maxMatches;
					service.updateCurrentMatch();
				} else {
					openGames.currentGame = Game.get({gameId: openGames.currentGameId}, function() {
						openGames.currentGame.matches = Match.query({gameId: openGames.currentGame.id}, function() {
							service.updateCurrentMatch();
						});
					});
				}

			} 
		},
		updateCurrentMatch: function() {
			if (angular.isDefined(openGames.currentGame)) {
				var maxMatches = openGames.currentGame.maxMatches;
				if (!angular.isDefined(openGames.currentMatchIndex)) {
					openGames.currentMatchIndex = Math.min(openGames.currentGame.numberOfCompletedGames, maxMatches - 1);
				}
				openGames.currentMatch = openGames.currentGame.matches[openGames.currentMatchIndex];
			}
		},
		loadOpenGames: function() {
			var me = this;
			var games = OpenGames.query({});
			games.$promise.then(function(loadedGames) {
				me.setGames(loadedGames);
			});
			return openGames;
			
		},
		getOpenGames: function() {
			return openGames;
		},
		deleteGame: function(game) {
		  var me = this;
	      Game.remove({gameId: game.id}, function() {
	    	  if (game.id === openGames.currentGameId) {
	    		  openGames.currentGameId = undefined;
	    	  }
	          me.loadOpenGames();
	      })
		},
		
		calculateRemainingMillis: function() {
		  var tableSums = {};
		  for (i = 0; i < openGames.gameList.length; i++) {
			  var currentGame = openGames.gameList[i];
			  var currentTableSum = (tableSums[currentGame.table] | 0);
			  tableSums[currentGame.table] = currentGame.estimatedRemainingMillis + currentTableSum;
			  currentGame.summedRemainingMilis = tableSums[currentGame.table];
		  }
		},
		setCurrentGame: function(gameId) {
			openGames.currentGameId = gameId;
			openGames.currentMatchIndex = undefined;
		},
		addPlayerScore: function(player) {
			
			if (angular.isDefined(openGames.currentGame)) {
				var maxGoals = openGames.currentGame.maxGoals;
				if (openGames.currentMatch.teamAScore < maxGoals
					&& openGames.currentMatch.teamBScore < maxGoals) {

					openGames.currentMatch.scorers.push(player);
					service.calculateScores(openGames.currentMatch);
					service.saveCurrentMatch();
					//service.checkEndOfMatch();
				}
			}
		},
		removeLastGoal: function() {
			if (openGames.currentMatch.scorers.length > 0) {
				var removedScorer = openGames.currentMatch.scorers.pop();
				openGames.isGameFinished = false;
				console.log("Removed scorer: " + removedScorer);
				service.calculateScores(openGames.currentMatch);
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
		    openGames.isMatchSaving = true;
		    openGames.currentMatch.lastUpdate = new Date();
		    openGames.currentMatch.$save({gameId: openGames.currentGame.id}, function(match) {
		      openGames.isMatchSaving = false;
		      service.checkEndOfMatch();
		    });
		},
		checkEndOfMatch: function() {
		    if (angular.isDefined(openGames.currentMatch)) {
		      var maxGoals = openGames.currentMatch.maxGoals;
		      var maxMatches = openGames.currentMatch.maxMatches;
		      if ((openGames.currentMatch.teamAScore >= maxGoals || openGames.currentMatch.teamBScore >=maxGoals)) {
			      openGames.currentMatch.endDate = new Date();
			      if (openGames.currentMatchIndex < maxMatches - 1) {  
			    	openGames.currentMatchIndex++;
			    	service.updateCurrentMatch();
			      } 
		      }
		      service.checkEndOfGame();
		    } 
		 },
		 checkEndOfGame: function() {
			 var currentGame = openGames.currentGame;
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
					 openGames.isGameFinished = true;
					 service.updateCurrentGame();
				 }
			 }
		 },
		 moveMatchIndexBy: function(increment) {
			  if (angular.isDefined(openGames.currentGame)) {
				  var maxMatches = openGames.currentGame.maxMatches;
				  var newMatchIndex = openGames.currentMatchIndex + increment;
				  if (newMatchIndex >=0 && newMatchIndex < maxMatches) {
					  openGames.currentMatchIndex = newMatchIndex;
					  service.updateCurrentGame();
				  }
			  }
		 }

	    
	 	
	};
	// Update the list, if a state changed on the server
	$rootScope.$on("UpdateOpenGames", function(event, message) {
	   console.log("Received change in open games from server");
	   service.setGames(message.openGames);
	});
	$rootScope.$on("UpdateMatch", function(event, message) {
	    console.log("Received match update from server");
	    var updatedGame = message.game;
	    var updatedMatch = new Match(message.match);
	    if (!angular.isDefined(openGames.gameMap[updatedGame.id])) {
	    	console.log("Game to update does not exist here anymore");
	    	//openGames.isGameFinished = true;
			if(!$rootScope.$$phase) {
				$rootScope.$apply();
			}
	    	return;
	    }
	    for (i = 0; i < openGames.gameList.length; i++) {
	    	if (openGames.gameList[i].id===updatedGame.id) {
	    		updatedGame.matches = openGames.gameList[i].matches;
	    		openGames.gameList[i] = updatedGame;
	    		var matchUpdateCandidate = openGames.gameList[i].matches[updatedMatch.matchIndex];
	    		if (matchUpdateCandidate.lastUpdate < updatedMatch.lastUpdate) {
	    			console.log("Match update is applicable.");
	    			openGames.gameList[i].matches[updatedMatch.matchIndex] = updatedMatch;
	    			service.updateCurrentGame();
	    			service.checkEndOfMatch();
	    		} else {
	    			console.log("Match update is outdated. Ignoring it.");
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