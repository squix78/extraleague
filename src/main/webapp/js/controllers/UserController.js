angular.module('Extraleague').controller('UserController', 
function UserController($scope, $rootScope, $resource, $location, $routeParams, CurrentUser) {
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