angular.module('Extraleague').controller('PlayerStatsController', 
function UserController($scope, $rootScope, $resource, $location, $routeParams, PlayerStats) {

		$scope.isPlayerStatsLoading = true;
		$scope.playerStats = PlayerStats.get({}, function() {
			$scope.isPlayerStatsLoading = false;
		});



	
});