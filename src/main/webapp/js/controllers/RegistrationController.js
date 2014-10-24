angular.module('Extraleague').controller('RegistrationController', 
function($scope, $http, Blobs, PlayerService, PlayerUsers) {
	$scope.blobUrl = Blobs.get({});
	$scope.player = new PlayerUsers();
	$scope.isRegistrationOK = false;
	$scope.isRegistrationFailed = false;

	
	$scope.register = function() {
		$scope.isRegistrationFailed = false;
		$scope.isRegistrationOK = false;
		$scope.isPlayerRegistering = true;
		$scope.tmpPlayer = PlayerService.savePlayer($scope.player);
		$scope.tmpPlayer.then(function(result) {
			$scope.isPlayerRegistering = false;
			$scope.player = new PlayerUsers();
			$scope.isRegistrationOK = true;
		}, function(error) {
			console.log("Error saving player: " + error)
			$scope.isRegistrationFailed = true;
		});
	};
	
});