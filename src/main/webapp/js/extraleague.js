angular.module('Extraleague', ['ngResource'])
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
	.factory('Match', ['$resource', function($resource) {
		return $resource('/rest/tables/:table/games/:gameId/matches');
	}]);

function MainController($scope, $resource, $location, Tables) {
	$scope.tablesLoading = true;
	$scope.tables = Tables.query({}, function() {
		$scope.tablesLoading = false;
	});
	
	$scope.selectTable = function(table) {
		console.log("Table selected: " + table.name);
		$location.path("/tables/" + table.name);
	};
}

function TableController($scope, $resource, $routeParams, $location, Games) {
	$scope.table = $routeParams.table;
	$scope.currentGame = new Games();
	$scope.currentGame.table= $scope.table;
	$scope.currentGame.players = [];
	$scope.updateGames = function() {
		$scope.games = Games.query({table: $scope.table}, function() {
			
		});
	};
	$scope.$watch('player', function(newValue, oldValue) {
		if (angular.isDefined(newValue)) {
			$scope.currentGame.players = newValue.split(' ');
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
	}
}
function GameController($scope, $resource, $routeParams, $location, Game, Match) {
	$scope.mutations = [[0,1,2,3], [3,0,1,2], [2,3,1,0], [0,2,3,1]];
	$scope.gameId = $routeParams.gameId;
	$scope.table = $routeParams.table;
	$scope.matches = [];
	$scope.matchIndex = 0;
	$scope.game = Game.get({table: $scope.table, gameId: $scope.gameId}, function() {
		var players = $scope.shuffle($scope.game.players);
		for (var i = 0; i < 4; i++) {
			var match = new Match();
			var currentPlayers = [];
			for (var j = 0; j < 4; j++) {
				currentPlayers.push(players[$scope.mutations[i][j]]);
			}
			match.teamA = [currentPlayers[0], currentPlayers[1]];
			match.teamB = [currentPlayers[2], currentPlayers[3]];
			match.teamAScore = 0;
			match.teamBScore = 0;
			match.gameId = $scope.gameId;
			$scope.matches.push(match);
		}
		$scope.updateCurrentMatch();
		
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
		if ($scope.match.teamAScore >= 5 || $scope.match.teamBScore >=5) {
			$scope.matchIsSaving = true;
			$scope.match.endDate = new Date();
			$scope.match.$save({table: $scope.table, gameId: $scope.gameId}, function(match) {
				$scope.matchIsSaving = false;
			});
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
	$scope.shuffle = function(o){ //v1.0
	    for(var j, x, i = o.length; i; j = Math.floor(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
	    return o;
	};
}
function SummaryController($scope, $resource, $routeParams, Summary) {
	$scope.table = $routeParams.table;
	$scope.gameId = $routeParams.gameId;
	$scope.summary = Summary.get({table: $scope.table, gameId: $scope.gameId}, function() {
		
	});
}