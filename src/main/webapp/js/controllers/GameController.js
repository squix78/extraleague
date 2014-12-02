angular.module('Extraleague').controller('GameController', 
function($scope, $rootScope, $resource, $routeParams, $location, $modal, Players, NotificationService, GameService, Mutations) {
  $scope.isMutationsLoading = true;
  $scope.mutations = Mutations.query({}, function() {
	$scope.isMutationsLoading = false;
  });
  
  $scope.gameId = $routeParams.gameId;
  $scope.table = $routeParams.table;
  $scope.matches = [];
  $scope.matchIndex = 0;

  $scope.$on('$viewContentLoaded', function() {
    $rootScope.backlink = '/tables/' + $scope.table;
  });
  $scope.openGames = GameService.loadOpenGames();
  GameService.setCurrentGame($scope.gameId);

  $scope.addPlayerScore = function(player) {
	console.log("Scored: " + player);
	GameService.addPlayerScore(player);
  };
  $scope.decreaseScore = function() {
	GameService.removeLastGoal();
  };

  
  $scope.$watch('openGames.isGameFinished', function(newValue, oldValue) { 
	  if (newValue && !oldValue) {
		  $scope.showSummary();
	  }
  });
  
  $scope.showSummary = function () {
	  $location.path("/games/" + $scope.gameId + "/summary");  
  };
  
  $scope.moveMatchIndexBy = function(increment) {
	  GameService.moveMatchIndexBy(increment);
  }
  
  $scope.closeAlert = function() {
	  GameService.hideAlert();
  }
  $scope.saveCurrentMatch = function() {
	  GameService.saveCurrentMatch();
  }
  
  $scope.swapTeamPositions = function(team) {
	  console.log("Team before swap: " + team);
	  var temp = team[0];
	  team[0] = team[1];
	  team[1] = temp;
	  console.log("Team after swap: " + team);
	  $scope.saveCurrentMatch();
  };
  $scope.swapTeamColors = function() {
	  GameService.swapTeamColors();
  };
  
  $scope.openSpecialEvent = function (size) {

	    var modalInstance = $modal.open({
	      templateUrl: 'js/templates/eventModal.html',
	      controller: 'EventModalController',
	      resolve: {
	        players: function () {
	          return GameService.getCurrentPlayers();
	        }
	      }
	    });

	    modalInstance.result.then(function (event) {
	      console.log(event);
	      GameService.addEvent(event);
	    }, function () {
	      //$log.info('Modal dismissed at: ' + new Date());
	    });
  }
  
});  
