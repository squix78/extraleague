angular.module('Extraleague')
.directive('compare', [ function() {
	return {
    	templateUrl: '/js/templates/compare.html',
		scope: {
			playerARanking: "=",
			playerBRanking: "=",
			compare: "@",
			label: "@",
			inverse: "="
		},
		compile: function(element, attrs){


		},
		controller: function($scope) {
			$scope.$watchCollection('[playerARanking.statistics, playerBRanking.statistics]', function(newValue, oldValues){
				if (angular.isDefined($scope.playerARanking) && angular.isDefined($scope.playerBRanking)) {
					$scope.valueA = $scope.playerARanking.statistics[$scope.compare];
					$scope.valueB = $scope.playerBRanking.statistics[$scope.compare];
					var calcValueA = $scope.valueA;
					var calcValueB = $scope.valueB;
					if ($scope.inverse) {
						calcValueA = $scope.valueB;
						calcValueB = $scope.valueA;
					}
					$scope.shareA = 100 * calcValueA / ($scope.valueA + $scope.valueB);
					$scope.shareB = 100 * calcValueB / ($scope.valueA + $scope.valueB);
					console.log($scope.shareA + ":" + $scope.shareB);
				}
			});

		},
		link: function(scope, elem, attrs) {
		}
	};
}])
.controller('HeadToHeadController', 
function PlayerController($scope, $rootScope, $routeParams, Player, Badges, PlayerService) {

	  $scope.$watch('players.playerA', function(newValue, oldValue) { 
		  if (newValue && PlayerService.isPlayerDefined(newValue)) {
			  console.log("Loading data player A");
			  $scope.isPlayerALoading = true;
			  $scope.players.playerARanking = Player.get({player: $scope.players.playerA}, function() {
			    $scope.isPlayerALoading = false;
			  });
		  }
	  });
	  
	  $scope.$watch('players.playerB', function(newValue, oldValue) { 
		  if (newValue && PlayerService.isPlayerDefined(newValue)) {
			  console.log("Loading data player A");
			  $scope.isPlayerBLoading = true;
			  $scope.players.playerBRanking = Player.get({player: $scope.players.playerB}, function() {
				  $scope.isPlayerBLoading = false;
			  });
		  }
	  });
	  

	  $scope.badgeMap = Badges.get(function() {
	    $scope.badgeList = [];
	    angular.forEach($scope.badges, function(key, value) {
	      $scope.badgeList.push(key);
	    });
	  });
});