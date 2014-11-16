angular.module('Extraleague').controller('PlayerStatsController', 
function UserController($scope, $rootScope, $resource, $location, $routeParams, PlayerStats) {

		$scope.isPlayerStatsLoading = true;
		$scope.playerStats = PlayerStats.get({}, function() {
			$scope.isPlayerStatsLoading = false;
		});

		$scope.isWeekend = function(value) {
			var date = new Date(value);
			if (date.getDay() == 0 || date.getDay() == 6) {
				return true;
			}
			return false;
		}

	
});