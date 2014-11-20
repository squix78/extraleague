angular.module('Extraleague').controller('EventModalController', 
function($scope, $modalInstance, players) {

	  $scope.players = players;
	  $scope.event = {
		 player: $scope.players[0]
	  };
	  
	  $scope.add = function () {
		    $scope.event.time = new Date();
		    $modalInstance.close($scope.event);
	  };
	  $scope.cancel = function() {
		  $modalInstance.dismiss('cancel');
	  }
});