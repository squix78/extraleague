angular.module('Extraleague', ['ngResource'])
    .config(function($routeProvider) {
      $routeProvider
      .when('/', {
        controller : 'MainController',
        templateUrl : 'partials/tables.html'
      })
      .when('/table/:table', {
    	  controller : 'TableController',
    	  templateUrl : 'partials/table.html'
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
	}]);

function MainController($scope, $resource, $location, Tables) {
	$scope.tablesLoading = true;
	$scope.tables = Tables.query({}, function() {
		$scope.tablesLoading = false;
	});
	
	$scope.selectTable = function(table) {
		console.log("Table selected: " + table.name);
		$location.path("/table/" + table.name);
	};
}

function TableController($scope, $resource, $routeParams, Games) {
	$scope.table = $routeParams.table;
	$scope.currentGame = new Games();
	$scope.currentGame.table= $scope.table;
	$scope.currentGame.players = [];
	$scope.updateGames = function() {
		$scope.games = Games.query({table: $scope.table}, function() {
			
		});
	};
	$scope.updateGames();
	$scope.addPlayer = function() {
		$scope.currentGame.players.push($scope.player);
		$scope.player="";
	};
	$scope.startGame = function() {
		$scope.currentGame = $scope.currentGame.$save({table: $scope.table});
		$scope.updateGames();
	}
}