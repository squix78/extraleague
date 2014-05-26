angular.module('Extraleague', ['ngResource', 'ngRoute', 'ngTouch', 'PlayerMappings', 'Charts', 'Games', 'ui.bootstrap', 'nvd3ChartDirectives', 'gaeChannelService'])
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
        .when('/meetingPoint', {
        	controller : 'MeetingPointController',
        	templateUrl : 'partials/meetingpoint.html'
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

    .factory('Summary', ['$resource', function($resource) {
      return $resource('/rest/games/:gameId/summary');
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
    .factory('MeetingPointPlayers', ['$resource', function($resource) {
    	return $resource('/rest/meetingPointPlayers');
    }])
    .factory('MeetingPointPlayer', ['$resource', function($resource) {
    	return $resource('/rest/meetingPointPlayers/:playerId');
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
    .factory('AuthUrl', ['$resource', function($resource) {
    	return $resource('/rest/authUrl');
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

function MainController($scope, $rootScope, $resource, $location, $routeParams, Tables, AuthUrl) {
  
  $rootScope.backlink = false;
  
  $rootScope.backAction = function() {
    $location.path($rootScope.backlink);
  }; 
  
  $scope.navigateTo = function(target) {
	$scope.navCollapsed = true
	$location.path(target);  
  };
  
  $scope.authUrl = AuthUrl.get({}, function() {
		
  });

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

function OpenGamesController($scope, $rootScope, $location, GameService) {
  $rootScope.backlink = false;
  $scope.updateGames = function() {
	  $scope.openGames = GameService.loadOpenGames();
  };
  
  $scope.updateGames();


  $scope.continueGame = function(game) {
      $location.path("/tables/" + game.table + "/games/" + game.id);
  };
  $scope.deleteGame = function(game) {
	  GameService.deleteGame(game);
  };

}
function GameController($scope, $rootScope, $resource, $routeParams, $location, Game, Match, Players, NotificationService, GameService) {
  
  $scope.gameId = $routeParams.gameId;
  $scope.table = $routeParams.table;
  $scope.matches = [];
  $scope.matchIndex = 0;

  $scope.$on('$viewContentLoaded', function() {
    $rootScope.backlink = '/tables/' + $scope.table;
  });
  $scope.openGames = GameService.loadOpenGames();
  GameService.setCurrentGame($scope.gameId);

  $scope.addPlayerScore = function(player) {
	console.log("Scored: " + player);
	GameService.addPlayerScore(player);
  };
  $scope.decreaseScore = function() {
	GameService.removeLastGoal();
  };

  
  $scope.$watch('openGames.isGameFinished', function(newValue, oldValue) { 
	  if (newValue && !oldValue) {
		  $scope.showSummary();
	  }
  });
  
  $scope.showSummary = function () {
	  $location.path("/tables/" + $scope.table + "/games/" + $scope.gameId + "/summary");  
  };
  
  $scope.moveMatchIndexBy = function(increment) {
	  GameService.moveMatchIndexBy(increment);
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
function MeetingPointController($scope, $rootScope, $timeout, $location, MeetingPointPlayers, MeetingPointPlayer, Tables, Games, NotificationService) {
	$scope.loadPlayers = function() {
		$scope.arePlayersLoading = true;
		$scope.players = MeetingPointPlayers.query({}, function() {
			$scope.arePlayersLoading = false;
		});
	};
	$scope.loadPlayers();
    $scope.tables = Tables.query({}, function() {
	    $scope.isTablesLoading = false;
    });
    $scope.availableTimes = [
       {label: 'next 15min', timeDiff: '15'},
       {label: 'next 30min', timeDiff: '30'},
       {label: 'next 1h', timeDiff: '60'},
       {label: 'next 2h', timeDiff: '120'}                   
    ];
    $scope.addPlayer = function() {
    	var availableUntil = new Date().getTime();
    	availableUntil += $scope.availableNextMin * 60 * 1000;
    	$scope.player.availableUntil = new Date(availableUntil);
    	$scope.player.player = $scope.player.player.toLowerCase();
    	var player = new MeetingPointPlayers($scope.player);
    	$scope.player = "";
    	player.$save({}, function() {
    		$scope.loadPlayers();
    	});
    };
    $scope.togglePlayer = function(player) {
    	player.enabled = ! (player.enabled | false);
    	console.log("Player: " + player.player + ", enabled: " +player.enabled );
    }
    $scope.startGame = function() {
    	var enabledPlayers = $scope.getEnabledPlayers();
    	if (enabledPlayers.length === 4) {
    		console.log("We could start playing");
    		var game = new Games();
    		game.table= enabledPlayers[0].table;
    		game.players = [];
    		angular.forEach(enabledPlayers, function(enabledPlayer) {
    			game.players.push(enabledPlayer.player);
    		});
    	    game.$save({table: game.table}, function(savedGame){
    	        $location.path("/tables/" + game.table + "/games/" + savedGame.id);      
    	    });
    	}
    }
    $scope.countEnabledPlayers = function() {
    	return $scope.getEnabledPlayers().length;
    }
    $scope.getEnabledPlayers = function() {
    	var enabledPlayers = [];
    	var now = new Date();
    	angular.forEach($scope.players, function(player) {
    		if (player.enabled && player.availableUntil > now) {
    			enabledPlayers.push(player);
    		}
    	});
    	return enabledPlayers;
    }
    $scope.refreshFilter = function() {
    	if(!$scope.$$phase) {
    		$scope.$apply();
    	}
		var timer = $timeout($scope.refreshFilter, 10000);
		$scope.$on('$locationChangeStart', function(){
			$timeout.cancel(timer);
		});
    }
    $scope.refreshFilter();
    $scope.filterOldEntries = function(element) {
    	var now = new Date();
    	return element.availableUntil > now;
    };
    $scope.removePlayer = function(player) {
    	var playerToDelete = new MeetingPointPlayer(player);
    	playerToDelete.$delete({playerId: player.id}, function() {
    		$scope.loadPlayers();
    	});
    }
    
	$rootScope.$on("UpdateMeetingPoint", function(event, message) {
		   console.log("Received update for meeting point");
		   $scope.players = message.players;
	       if(!$scope.$$phase) {
	         $scope.$apply();
	       }
	});

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
