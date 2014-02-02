angular.module('Extraleague', ['ngResource', 'PlayerMappings'])
    .config(function($routeProvider) {
      $routeProvider
      .when('/', {
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
      .when('/ranking', {
    	  controller : 'RankingController',
    	  templateUrl : 'partials/ranking.html'
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
	.factory('Game', ['$resource', function($resource) {
		return $resource('/rest/tables/:table/games/:gameId');
	}])
	.factory('Summary', ['$resource', function($resource) {
		return $resource('/rest/tables/:table/games/:gameId/summary');
	}])
	.factory('Ranking', ['$resource', function($resource) {
		return $resource('/rest/ranking');
	}])
	.factory('Match', ['$resource', function($resource) {
		return $resource('/rest/tables/:table/games/:gameId/matches');
	}]);

function MainController($scope, $resource, $location, Tables) {
//	$scope.tablesLoading = true;
//	$scope.tables = Tables.query({}, function() {
//		$scope.tablesLoading = false;
//	});
	$scope.tables = [{name: 'Park'}, {name: 'Albis'}, {name: 'Bern'}, {name: 'Skopje'}];
	
	$scope.selectTable = function(table) {
		console.log("Table selected: " + table.name);
		$location.path("/tables/" + table.name);
	};
}

function TableController($scope, $resource, $routeParams, $location, Games, PlayerService) {
	$scope.PlayerService = PlayerService;
	$scope.table = $routeParams.table;
	$scope.currentGame = new Games();
	$scope.currentGame.table= $scope.table;
	$scope.currentGame.players = [];
	$scope.updateGames = function() {
		$scope.isGamesLoading = true;
		$scope.games = Games.query({table: $scope.table}, function() {
			$scope.isGamesLoading = false;
		});
	};
	$scope.$watch('player', function(newValue, oldValue) {
		if (angular.isDefined(newValue)) {
			$scope.currentGame.players = newValue.toLowerCase().split(' ');
			console.log(newValue +", " + $scope.currentGame.players);
		}
		
	});
	$scope.updateGames();
	$scope.addPlayer = function() {
		$scope.currentGame.players.push($scope.player);
		$scope.player="";
	};
	$scope.startGame = function() {
		$scope.currentGame.$save({table: $scope.table}, function(savedGame){
			$scope.currentGame = savedGame;
			$location.path("/tables/" + $scope.table + "/games/" + $scope.currentGame.id);			
		});
	};
	$scope.continueGame = function(gameId) {
		$location.path("/tables/" + $scope.table + "/games/" + gameId);
	};
}
function GameController($scope, $resource, $routeParams, $location, Game, Match, PlayerService) {
	$scope.PlayerService = PlayerService;
	
	$scope.gameId = $routeParams.gameId;
	$scope.table = $routeParams.table;
	$scope.matches = [];
	$scope.matchIndex = 0;
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
		$scope.checkNextMatch();
	};
	$scope.decreaseScoreTeamA = function() {
		if ($scope.match.teamAScore > 0) {
			$scope.match.teamAScore--;
		}
	};
	$scope.checkNextMatch = function() {
		$scope.match.$save({table: $scope.table, gameId: $scope.gameId}, function(match) {
			$scope.matchIsSaving = false;
		});
		if ($scope.match.teamAScore >= 5 || $scope.match.teamBScore >=5) {
			$scope.matchIsSaving = true;
			$scope.match.endDate = new Date();
			if ($scope.matchIndex < 3) {	
				$scope.matchIndex++;
				$scope.updateCurrentMatch();
			} else {
				$location.path("/tables/" + $scope.table + "/games/" + $scope.gameId + "/summary");				
			}
		}
	}
	$scope.increaseScoreTeamB = function() {
		$scope.match.teamBScore++;
		$scope.checkNextMatch();
	}
	$scope.decreaseScoreTeamB = function() {
		if ($scope.match.teamBScore > 0) {
			$scope.match.teamBScore--;
		}
	}
	console.log("Arrived in GameController: " + $scope.gameId);
	$scope.updateCurrentMatch = function() {
		$scope.match = $scope.matches[$scope.matchIndex];
		$scope.match.startDate = new Date();
	}

}
function SummaryController($scope, $resource, $routeParams, Summary) {
	$scope.table = $routeParams.table;
	$scope.gameId = $routeParams.gameId;
	$scope.summary = Summary.get({table: $scope.table, gameId: $scope.gameId}, function() {
		
	});
}
function RankingController($scope, $resource, $routeParams, Ranking) {
	$scope.rankings = Ranking.query({}, function() {
		
	});
}