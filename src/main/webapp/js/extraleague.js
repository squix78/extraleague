angular.module('Extraleague', ['ngResource', 'ngRoute', 'ngTouch', 'PlayerMappings', 'Charts', 'ui.bootstrap', 'nvd3ChartDirectives', 'gaeChannelService'])
    .config(function($routeProvider) {
        $routeProvider.when('/tables', {
           controller : 'TablesController',
           templateUrl : 'partials/tables.html'
        })
        .when('/tables/:table', {
           controller : 'TableController',
           templateUrl : 'partials/table.html'
        })
        .when('/tables/:table/games/:gameId', {
           controller : 'GameController',
           templateUrl : 'partials/game.html'
        })
        .when('/tables/:table/games/:gameId/summary', {
           controller : 'SummaryController',
           templateUrl : 'partials/summary.html'
        })
        .when('/openGames', {
           controller : 'OpenGamesController',
           templateUrl : 'partials/currentlyOpenGames.html'
        })
         .when('/playedGames', {
           controller : 'PlayedGamesController',
           templateUrl : 'partials/playedGames.html'
        })
        .when('/stats', {
        	controller : 'StatsController',
        	templateUrl : 'partials/stats.html'
        })
        .when('/ranking', {
           controller : 'RankingController',
           templateUrl : 'partials/ranking.html'
        })
        .when('/ranking/tag/:tag', {
        	controller : 'RankingByTagController',
        	templateUrl : 'partials/ranking.html'
        })
        .when('/player/:player', {
           controller : 'PlayerController',
           templateUrl : 'partials/player.html'
        })
        .when('/highlights', {
        	controller : 'HighlightsController',
        	templateUrl : 'partials/highlights.html'
        })
        .when('/about', {
           controller : 'AboutController',
           templateUrl : 'partials/about.html'
        })
        .otherwise({
           redirectTo: '/highlights'
        });
    })
    .factory('Ping', ['$resource', function($resource) {
      return $resource('/rest/ping');
    }])
    .factory('Tables', ['$resource', function($resource) {
      return $resource('/rest/tables');
    }])
    .factory('Games', ['$resource', function($resource) {
      return $resource('/rest/tables/:table/games');
    }])
    .factory('OpenGames', ['$resource', function($resource) {
      return $resource('/rest/openGames');
    }])
    .factory('PlayedGames', ['$resource', function($resource) {
      return $resource('/rest/playedGames');
    }])
    .factory('Game', ['$resource', function($resource) {
      return $resource('/rest/tables/:table/games/:gameId');
    }])
    .factory('Summary', ['$resource', function($resource) {
      return $resource('/rest/tables/:table/games/:gameId/summary');
    }])
    .factory('Ranking', ['$resource', function($resource) {
      return $resource('/rest/ranking');
    }])
    .factory('RankingByTag', ['$resource', function($resource) {
    	return $resource('/rest/rankings/tags/:tag');
    }])
    .factory('Player', ['$resource', function($resource) {
      return $resource('/rest/players/:player');
    }])
    .factory('Players', ['$resource', function($resource) {
      return $resource('/rest/players');
    }])
    .factory('TimeSeries', ['$resource', function($resource) {
      return $resource('/rest/timeseries/:player');
    }])
    .factory('Statistics', ['$resource', function($resource) {
    	return $resource('/rest/statistics');
    }])
    .factory('Badges', ['$resource', function($resource) {
        return $resource('/rest/badges', {}, {get: {cache: true, method: 'GET' } } );
    }])
    .factory('Mutations', ['$resource', function($resource) {
    	return $resource('/rest/mutations');
    }])
    .factory('Match', ['$resource', function($resource) {
      return $resource('/rest/tables/:table/games/:gameId/matches');
    }])
    .directive('badges', function() {
      return {
        restrict: 'A',
        require: '^ngModel',
        templateUrl: 'templates/badges.html',
          scope: {
              ngModel: '=',
              badgeMap: '='
          }
      };
    })
    .directive('highlightBadges', ['Badges', function(Badges) {
	  return {    
	    restrict: 'A',    
	    require: 'ngModel',
	    replace: true,   
	    scope: { ngModel: '=ngModel' },
	    link: function compile(scope, element, attrs, controller) {         
	        scope.$watch('ngModel', function(value) { 
	        	var badgeMap = Badges.get({}, function() {
	        		
		        	if (angular.isDefined(value) && typeof value === 'string') {
			        	angular.forEach(badgeMap, function(badge, name) {
				            angular.forEach(value.match(badge.jsRegex), function(word) {
				            	if (angular.isDefined(badge) && angular.isDefined(badge.badgeType)) {
				            		value = value.replace(word, 
				            				'<span class="label label-primary ' 
				            				+ badge.badgeType + '" title="' 
				            				+ badge.description + '"> <i class="fa ' 
				            				+ badge.faClass + '"></i> ' + word + '</span>');
				            	}
				            });
				            element.html(value); 
			        	});
		        	}
	        	});
	          });                
	    }
	  };  
	}])
	.directive('extraleagueNavbar', function ($location) {
	  return {
	    restrict: 'A',
        link: function link(scope, element) {
          var listener = function () {
            return $location.path();
          };
          var changeHandler = function (newValue) {
            $('li[data-match-route]', element).each(function (k, li) {
              var $li = angular.element(li);
              var pattern = $li.attr('data-match-route');
              var regexp = new RegExp('^' + pattern + '$', 'i');
              if (regexp.test(newValue)) {
                $li.addClass('active');
              } else {
                $li.removeClass('active');
              }
		    });
		  };
          scope.$watch(listener, changeHandler);
      	}
	  };
  	});

function MainController($scope, $rootScope, $resource, $location, $routeParams, Tables) {
  
  $rootScope.backlink = false;
  
  $rootScope.backAction = function() {
    $location.path($rootScope.backlink);
  }; 
  
  $scope.navigateTo = function(target) {
	$scope.navCollapsed = true
	$location.path(target);  
  };

}

function TablesController($scope, $rootScope, $resource, $location, $routeParams, Tables) {
  $scope.isTablesLoading = true;
  $scope.tables = Tables.query({}, function() {
	  $scope.isTablesLoading = false;
  });
  
  $rootScope.backlink = false;
  
  $scope.selectTable = function(table) {
    console.log("Table selected: " + table.name);
    $location.path("/tables/" + table.name);
  };
  
  $rootScope.backAction = function() {
    $location.path($rootScope.backlink);
  };

}

function TableController($scope, $rootScope, $resource, $routeParams, $location, Games, Game, Players) {
   $scope.isSavingGame = false;
   $scope.table = $routeParams.table;
   $scope.currentGame = new Games();
   $scope.currentGame.table= $scope.table;
   $scope.currentGame.players = [];

   $scope.$on('$viewContentLoaded', function() {
     $rootScope.backlink = '/tables';
   });

   $scope.$watch('player', function(newValue, oldValue) {
      if (angular.isDefined(newValue)) {
          $scope.currentGame.players = newValue.toLowerCase().replace(/,/g,'').split(' ');
          console.log(newValue +", " + $scope.currentGame.players);
      }
  
   });

  $scope.addPlayer = function() {
    $scope.currentGame.players.push($scope.player);
    $scope.player="";
  };
  
  $scope.startGame = function() {
    $scope.isSavingGame = true;
    $scope.currentGame.$save({table: $scope.table}, function(savedGame){
      $scope.isSavingGame = false;
      $scope.currentGame = savedGame;
      $location.path("/tables/" + $scope.table + "/games/" + $scope.currentGame.id);      
    });
  };
  
  $scope.continueGame = function(gameId) {
    $location.path("/tables/" + $scope.table + "/games/" + gameId);
  };
  
  $scope.deleteGame = function(gameId) {
    Game.remove({table: $scope.table, gameId: gameId});
    $scope.updateGames();
  };
  
  var playersResult = Players.get({}, function() {
    $scope.players = playersResult;
  });
}

function PlayedGamesController($scope, $rootScope, $resource, $timeout, $routeParams, $location, $filter, PlayedGames, Players) {
    $scope.isPlayedGamesLoading = true;
    PlayedGames.query(function (response) {  
        $scope.isPlayedGamesLoading = false;   
        $scope.playedGames = response;   
        $scope.players = [];
        $scope.playedGames.forEach(function(game) {  $scope.players = $scope.players.concat(game.players) });
        
        $scope.players = $scope.players.filter(function onlyUnique(value, index, self) { 
          return self.indexOf(value) === index;
        });                 
    });
}

function OpenGamesController($scope, $rootScope, $resource, $timeout, $routeParams, $location, $filter, OpenGames, Game, Match, Players, NotificationService) {
  $rootScope.backlink = false;
  $scope.updateGames = function() {
      $scope.isGamesLoading = true;
      $scope.games = OpenGames.query({}, function() {
              $scope.isGamesLoading = false;
      });
  };
  $scope.updateGames();
  
  // Update the list, if a state changed on the server
  $rootScope.$on("UpdateOpenGames", function(event, message) {
     console.log("Received change in open games from server");
     $scope.$apply(function() {
        $scope.games = [];
        angular.forEach(message.openGames, function(key, value) {
          $scope.games.push(new OpenGames(key));
        });
    });
  });
  
  $rootScope.$on("UpdateMatch", function(event, message) {
	    console.log("Received change in game from server");
	    $scope.$apply(function() {
	    	var game = new Game(message.game);
	    	var match = new Match(message.match);
	    	game.currentMatch = match;
	    	for (var i=0; i<$scope.games.length; i++) {
	    		if ($scope.games[i].id === game.id) {
	    			//$scope.games[i].gameProgress = game.gameProgress;
	    			angular.copy(game, $scope.games[i]);
	    		}
	    	}
	    });
  });

  $scope.continueGame = function(game) {
      $location.path("/tables/" + game.table + "/games/" + game.id);
  };
  $scope.deleteGame = function(game) {
      Game.remove({table: game.table, gameId: game.id}, function() {
        $scope.updateGames();
      });
  };
  
  $scope.getSumEstimatedRemainingMillis = function(index) {
	  var sum = 0;
	    var selectedGames = $scope.games.slice(0, index + 1);
	    angular.forEach(selectedGames, function(game, index){
	      sum += game.estimatedRemainingMillis;
	    });
	    return sum;
  };
}
function GameController($scope, $rootScope, $resource, $routeParams, $location, Game, Match, Players, NotificationService) {
  
  $scope.gameId = $routeParams.gameId;
  $scope.table = $routeParams.table;
  $scope.matches = [];
  $scope.matchIndex = 0;

  $scope.$on('$viewContentLoaded', function() {
    $rootScope.backlink = '/tables/' + $scope.table;
  });

  $scope.game = Game.get({table: $scope.table, gameId: $scope.gameId}, function() {
      $scope.matchIndex = Math.min($scope.game.numberOfCompletedGames, 3);
      if (!angular.isDefined($scope.matchIndex)) {
        $scope.matchIndex = 0;
      }
      $scope.matches = Match.query({table: $scope.table, gameId: $scope.gameId}, function() {
        $scope.updateCurrentMatch();
      });
  });
  $scope.increaseScoreTeamA = function(player) {
	console.log(player);
	if ($scope.match.teamAScore < 5) {
	    $scope.match.teamAScore++;
	    $scope.match.scorers.push(player);
	    $scope.saveMatch();
	}
  };
  $scope.decreaseScore = function() {
    if ($scope.match.scorers.length > 0) {
      var lastScorer = $scope.match.scorers.pop();
      
      if ($scope.match.teamA.indexOf(lastScorer) > -1) {
    	  $scope.match.teamAScore--;
      } else {
    	  $scope.match.teamBScore--;
      }
      $scope.saveMatch();
    }
  };
  $scope.saveMatch = function() {
    $scope.matchIsSaving = true;
    $scope.match.$save({table: $scope.table, gameId: $scope.gameId}, function(match) {
      $scope.matchIsSaving = false;
      $scope.checkEndOfMatch();
    });
  }
  $scope.checkEndOfMatch = function() {
    if ($scope.match.teamAScore >= 5 || $scope.match.teamBScore >=5) {
      $scope.match.endDate = new Date();
      if ($scope.matchIndex < 3) {  
        $scope.matchIndex++;
        $scope.updateCurrentMatch();
      } else {
        $location.path("/tables/" + $scope.table + "/games/" + $scope.gameId + "/summary");        
      } 
    } 
  };
  $scope.finishedMatches = function() {
	  var finishedMatches = 0;
	  angular.forEach($scope.matches, function(match, key) {
		  var maxScore = Math.max(match.teamAScore, match.teamBScore);
		  if (maxScore == 5) {
			  finishedMatches++;
		  }
	  });
	  return finishedMatches;
  };
  $scope.showSummary = function () {
	  $location.path("/tables/" + $scope.table + "/games/" + $scope.gameId + "/summary");  
  };
  
//  else {
//      $location.path("/tables/" + $scope.table + "/games/" + $scope.gameId + "/summary");        
//    }
  
  $scope.increaseScoreTeamB = function(player) {
	console.log(player);
	if ($scope.match.teamBScore < 5) {
	    $scope.match.teamBScore++;
	    $scope.match.scorers.push(player);    	
	    $scope.saveMatch();
	}
  };

  console.log("Arrived in GameController: " + $scope.gameId);
  $scope.updateCurrentMatch = function() {
    $scope.match = $scope.matches[$scope.matchIndex];
    $scope.match.startDate = new Date();
  }
  
  $scope.moveMatchIndexBy = function(increment) {
	  var newMatchIndex = $scope.matchIndex + increment;
	  if (newMatchIndex >=0 && newMatchIndex <=3) {
		  $scope.matchIndex = newMatchIndex;
		  $scope.updateCurrentMatch();
	  }
  }

}

function SummaryController($scope, $rootScope, $resource, $routeParams, $location, Summary, Match) {
  $scope.table = $routeParams.table;
  $scope.gameId = $routeParams.gameId;
  $rootScope.backlink = false;
  
  $scope.saveMatch = function(match) {
    $scope.isMatchSaving = true;
    match.$save({table: $scope.table, gameId: $scope.gameId}, function(match) {
      $scope.isMatchSaving = false;
      $scope.getSummary();
    });
  };
  $scope.matches = Match.query({table: $scope.table, gameId: $scope.gameId}, function() {

  });
  $scope.getSummary = function() {
    $scope.summary = Summary.get({table: $scope.table, gameId: $scope.gameId}, function() {
      
    });
  };
  $scope.getSummary();
  $scope.editScores = function () {
	  $location.path("/tables/" + $scope.table + "/games/" + $scope.gameId);  
  };
}

function RankingController($scope, $rootScope, $resource, $routeParams, Ranking, Badges, Tables) {
  
  $scope.predicate = [ '-successRate', '-goalPlusMinus'];
  $scope.isRankingLoading = true;
  $rootScope.backlink = false;
  $scope.rankings = Ranking.query({}, function() {
    $scope.isRankingLoading = false;
    
  });
  $scope.tables = Tables.query();
  //extract this to a service to avoid duplication and provide caching
  $scope.badgeMap = Badges.get(function() {
    $scope.badgeList = [];
    angular.forEach($scope.badgeMap, function(key, value) {
      $scope.badgeList.push(key);
    });
  });
}

function RankingByTagController($scope, $rootScope, $resource, $routeParams, RankingByTag, Badges, Tables) {
	$scope.tag = $routeParams.tag;
	$scope.predicate = [ '-successRate', '-goalPlusMinus'];
	$scope.isRankingLoading = true;
	$rootScope.backlink = false;
	$scope.rankings = RankingByTag.query({tag: $scope.tag}, function() {
		$scope.isRankingLoading = false;
		
	});
	
	$scope.tables = Tables.query();
	// extract this to a service to avoid duplication and provide caching
	$scope.badgeMap = Badges.get(function() {
		$scope.badgeList = [];
		angular.forEach($scope.badgeMap, function(key, value) {
			$scope.badgeList.push(key);
		});
	});
}

function PlayerController($scope, $rootScope, $routeParams, Player, TimeSeries, Badges) {
  $scope.player = $routeParams.player;
  $scope.isPlayerLoading = true;

  $scope.$on('$viewContentLoaded', function() {
    $rootScope.backlink = '/ranking';
  });
  
  $rootScope.backlink = '/ranking';
  
  $scope.playerResult = Player.get({player: $scope.player}, function() {
    $scope.isPlayerLoading = false;
  });
  
  $scope.isTimeseriesLoading = true;
  $scope.timeseries = TimeSeries.get({player: $scope.player}, function() {
    $scope.isTimeseriesLoading = false;
  });
  
  $scope.toolTipContentFunction = function(){
    return function(key, x, y) {
        return  '<h4>' + key + '</h4>' +
              '<p>' +  y + ' at ' + x + '</p>'
    };
  };
  $scope.xAxisTickFormatFunction = function(){
    return function(d){
      return d3.time.format('%y-%m-%d')(new Date(d)); //uncomment for date format
    };
  };
  $scope.yAxisPercentFormatFunction = function(){
    return function(d){
      return d3.format(".0%")(d);
    };
  };
  $scope.yAxisFormatFunction = function(){
	  return function(d){
		  return d; //uncomment for date format
	  };
  };
  $scope.badgeMap = Badges.get(function() {
    $scope.badgeList = [];
    angular.forEach($scope.badges, function(key, value) {
      $scope.badgeList.push(key);
    });
  });
}
function StatsController($scope, $rootScope, $routeParams, Statistics) {
	$scope.isStatisticsLoading = true;
	$scope.statistics = Statistics.get({}, function() {
		$scope.isStatisticsLoading = false;
		console.log($scope.hourHistogram.length);		
		$scope.hourHistogram = [];
		angular.forEach($scope.statistics.hourHistogram, function(value, key) {
			console.log($scope.hourHistogram);
			$scope.hourHistogram.push(value);
			console.log($scope.hourHistogram.length);		
		});

	});
	
	$scope.hourHistogram = [{ "key": 0 , "value": 0.25}, { "key": 1 , "value": 0.75} ];
}
function HighlightsController($scope, Mutations) {
	$scope.isMutationsLoading = true;
	$scope.mutations = Mutations.query({}, function() {
		$scope.isMutationsLoading = false;
	})
}
function AboutController($scope, $http) {
    var url = 'https://api.github.com/repos/squix78/extraleague/commits';
    $http.get(url).success(function(data) {
        $scope.commits = data;
    });
}
