angular.module('Extraleague').controller('UserController', 
function UserController($scope, $rootScope, $resource, $location, $routeParams, CurrentUser, PlayerStats) {
	$scope.isPlayerLoading = false;
	$scope.isPlayerSaving = false;
	$scope.loadPlayer = function() {
		$scope.isPlayerLoading = true;
		$scope.playerUser = CurrentUser.get({}, function() {
			console.log($scope.playerUser);
			$scope.isPlayerLoading = false;
			if (!angular.isDefined($scope.playerUser.player)) {
				console.log("found no user");
				$location.path("/claimUser");
			} else {
				$scope.isPlayerStatsLoading = true;
				$scope.playerStats = PlayerStats.get({player: $scope.playerUser.player}, function() {
					$scope.isPlayerStatsLoading = false;
				});
			}
		});
	};
	$scope.loadPlayer();
	$scope.savePlayer = function() {
		$scope.isPlayerSaving = true;
		$scope.playerUser.$save({}, function() {
			$scope.isPlayerSaving = false;
			
		});
	}


	
});