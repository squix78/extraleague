angular.module('Extraleague').controller('ChallengeController', 
function($scope, $routeParams, ChallengerTeam) {
	$scope.table = $routeParams.table;
	$scope.challengerTeam = new ChallengerTeam();
	$scope.challengerTeam.table = $scope.table;
	$scope.loadChallengers = function() {
		$scope.isChallengersLoading = true;
		$scope.challengers = ChallengerTeam.query({table: $scope.table}, function() {
			$scope.isChallengersLoading = false;
		});	
	};
	$scope.loadChallengers();
	
	$scope.addChallengers = function() {
		$scope.isAddingChallengers = true;
		$scope.challengerTeam.$save({table: $scope.table}, function() {
			$scope.isAddingChallengers = false;
			$scope.challengerTeam = new ChallengerTeam();
			$scope.loadChallengers();
			
		});
	};
	
	$scope.deleteChallenger = function(challenger) {
		$scope.isTeamDeleting = true;
		challenger.$remove({table: $scope.table, id: challenger.id}, function() {
			$scope.isTeamDeleting = false;
			$scope.loadChallengers();
		});
	};
	
});