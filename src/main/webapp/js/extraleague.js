angular.module('Extraleague', ['ngResource', 'ngRoute', 'PlayerMappings', 'ui.bootstrap', 'nvd3ChartDirectives', 'gaeChannelService'])
    .config(function($routeProvider) {
        $routeProvider.when('/', {
           controller : 'MainController',
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
        .when('/ranking', {
           controller : 'RankingController',
           templateUrl : 'partials/ranking.html'
        })
        .when('/player/:player', {
           controller : 'PlayerController',
           templateUrl : 'partials/player.html'
        })
        .otherwise({
           controller : 'MainController',
           templateUrl : 'partials/tables.html'
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
    .factory('Game', ['$resource', function($resource) {
      return $resource('/rest/tables/:table/games/:gameId');
    }])
    .factory('Summary', ['$resource', function($resource) {
      return $resource('/rest/tables/:table/games/:gameId/summary');
    }])
    .factory('Ranking', ['$resource', function($resource) {
      return $resource('/rest/ranking');
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
    .factory('Badges', ['$resource', function($resource) {
        return $resource('/rest/badges', {}, {get: {cache: true, method: 'GET' } } );
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
      }
    });

function MainController($scope, $rootScope, $resource, $location, $routeParams, Tables) {
  $scope.tables = [{name: 'Park'}, {name: 'Albis'}, {name: 'Bern'}, {name: 'Skopje'}];
  
  $rootScope.backlink = false;
  
  $scope.selectTable = function(table) {
    console.log("Table selected: " + table.name);
    $location.path("/tables/" + table.name);
  };
  
  $scope.isActive = function(path) {
     if ($location.path().indexOf(path) !== -1) {
        return "active";
     } else {
        return "";
     }
  };
  
  $rootScope.backAction = function() {
    $location.path($rootScope.backlink);
  };

}

function TableController($scope, $rootScope, $resource, $routeParams, $location, Games, Game, PlayerService, Players) {
   $scope.isSavingGame = false;
   $scope.PlayerService = PlayerService;
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
function OpenGamesController($scope, $rootScope, $resource, $timeout, $routeParams, $location, OpenGames, Game, PlayerService, Players, NotificationService) {
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
	    	for (var i=0;i<$scope.games[i].length;i++) {
	    		if ($scope.games[i].id === game.id) {
	    			$scope.games[i] = game;
	    		}
	    	})
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
}
function GameController($scope, $rootScope, $resource, $routeParams, $location, Game, Match, PlayerService, Players, NotificationService) {
  $scope.PlayerService = PlayerService;
  
  $scope.gameId = $routeParams.gameId;
  $scope.table = $routeParams.table;
  $scope.matches = [];
  $scope.matchIndex = 0;

  $scope.$on('$viewContentLoaded', function() {
    $rootScope.backlink = '/tables/' + $scope.table;
  });

  $scope.game = Game.get({table: $scope.table, gameId: $scope.gameId}, function() {
    if ($scope.game.numberOfCompletedGames >= 4) {
      $location.path("/tables/" + $scope.table + "/games/" + $scope.gameId + "/summary");
    } else {
      $scope.matchIndex = $scope.game.numberOfCompletedGames;
      if (!angular.isDefined($scope.matchIndex)) {
        $scope.matchIndex = 0;
      }
      $scope.matches = Match.query({table: $scope.table, gameId: $scope.gameId}, function() {
        $scope.updateCurrentMatch();
      });
    }
    
  });
  $scope.increaseScoreTeamA = function() {
    $scope.match.teamAScore++;
    $scope.saveMatch();
  };
  $scope.decreaseScoreTeamA = function() {
    if ($scope.match.teamAScore > 0) {
      $scope.match.teamAScore--;
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
  
  $rootScope.$on("UpdateMatch", function(event, message) {
    console.log("Received change in game from server");
    $scope.$apply(function() {
      if (message.game.id === $scope.game.id) {
        $scope.match = new Match(message.match);
        $scope.game = new Game(message.game);
        $scope.checkEndOfMatch();
      } else {
        console.log("Received update notification for another game");
      }
    });
  });
  
  $scope.increaseScoreTeamB = function() {
    $scope.match.teamBScore++;
    $scope.saveMatch();
  }
  $scope.decreaseScoreTeamB = function() {
    if ($scope.match.teamBScore > 0) {
      $scope.match.teamBScore--;
      $scope.saveMatch();
    }
  }
  console.log("Arrived in GameController: " + $scope.gameId);
  $scope.updateCurrentMatch = function() {
    $scope.match = $scope.matches[$scope.matchIndex];
    $scope.match.startDate = new Date();
  }

}

function SummaryController($scope, $rootScope, $resource, $routeParams, Summary, Match) {
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
}

function RankingController($scope, $rootScope, $resource, $routeParams, Ranking, Badges) {
  $scope.predicate = '-successRate';
  $scope.isRankingLoading = true;
  $rootScope.backlink = false;
  $scope.rankings = Ranking.query({}, function() {
    $scope.isRankingLoading = false;
    
  });
  $scope.badgeMap = Badges.get(function() {
    $scope.badgeList = [];
    angular.forEach($scope.badgeMap, function(key, value) {
      $scope.badgeList.push(key);
    });
  });
}

function PlayerController($scope, $rootScope, $routeParams, PlayerService, Player, TimeSeries, Badges) {
  $scope.player = $routeParams.player;
  $scope.playerPicture = PlayerService.getPlayerPicture($scope.player);
  $scope.isPlayerLoading = true;

  $scope.$on('$viewContentLoaded', function() {
    $rootScope.backlink = '/ranking';
  });
  $rootScope.backlink = '/ranking';
  $scope.playerResult = Player.get({player: $scope.player}, function() {
    $scope.isPlayerLoading = false;
  });
  $scope.isTimeseriesLoading = true;
  $scope.timeseries = TimeSeries.get({player: $scope.player}), function() {
    $scope.isTimeseriesLoading = false;
  };
  $scope.toolTipContentFunction = function(){
    return function(key, x, y, e, graph) {
        return  '<h4>' + key + '</h4>' +
              '<p>' +  y + ' at ' + x + '</p>'
    }
  };
  $scope.xAxisTickFormatFunction = function(){
    return function(d){
      return d3.time.format('%y-%m-%d')(new Date(d)); //uncomment for date format
    };
  };
  $scope.yAxisPercentFormatFunction = function(){
    return function(d){
      return d * 100 + "%"; //uncomment for date format
    };
  };
  $scope.badgeMap = Badges.get(function() {
    $scope.badgeList = [];
    angular.forEach($scope.badges, function(key, value) {
      $scope.badgeList.push(key);
    });
  });
}




