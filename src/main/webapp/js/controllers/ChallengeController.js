angular.module('Extraleague').controller('ChallengeController', 
function($rootScope, $scope, $routeParams, $location, ChallengerTeam, WinnerTeam, Game) {
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
	
	$scope.loadWinners = function() {
		$scope.isWinnersLoading = true;
		$scope.winnerTeam = WinnerTeam.get({table: $scope.table}, function() {
			$scope.isWinnersLoading = false;
		});			
	};
	$scope.loadWinners();
	
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
	
	$scope.startChallenge = function(challenger) {
		  $scope.game = new Game();
		  $scope.game.table = $scope.winnerTeam.table;
		  $scope.game.gameMode = $scope.winnerTeam.gameMode;
		  $scope.game.players = $scope.winnerTeam.winners.concat(challenger.challengers);
		  $scope.isGameStarting = true;
		  $scope.game.$save(function(savedGame) {
			  $scope.isGameStarting = false;
			  $location.path("/games/" + savedGame.id);
		  });
	};
	$rootScope.$on("UpdateChallengers", function(event, message) {
		   console.log("Received change challenger list");
		   $scope.loadChallengers();
	});
	$rootScope.$on("UpdateWinners", function(event, message) {
		console.log("Received change for winners");
		$scope.loadWinners();
	});
	
});