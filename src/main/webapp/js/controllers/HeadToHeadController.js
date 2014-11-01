angular.module('Extraleague')
.directive('compare', [function() {
	return {
    	templateUrl: '/js/templates/compare.html',
		scope: {
			playerARanking: "=",
			playerBRanking: "=",
			compare: "@",
			label: "@",
			inverse: "=",
			percentage: "=",
			precision: "=?",
			offset: "=?"
		},
		compile: function(element, attrs){


		},
		controller: function($scope, $filter) {
			$scope.$watchCollection('[playerARanking.statistics, playerBRanking.statistics]', function(newValue, oldValues){
				if (angular.isDefined($scope.playerARanking) 
						&& angular.isDefined($scope.playerBRanking)
						&& angular.isDefined($scope.playerARanking.statistics) 
						&& angular.isDefined($scope.playerBRanking.statistics)) {
					if (!angular.isDefined($scope.precision)) {
						$scope.precision = 0;
					}
					if (!angular.isDefined($scope.offset)) {
						$scope.offset = 0;
					}
					
					var valueA = $scope.playerARanking.statistics[$scope.compare];
					var valueB = $scope.playerBRanking.statistics[$scope.compare];
					var calcValueA = valueA + $scope.offset;
					var calcValueB = valueB + $scope.offset;
					if ($scope.inverse) {
						calcValueA = valueB + $scope.offset;
						calcValueB = valueA + $scope.offset;
					}
					$scope.displayValueA = $filter('number')(valueA, $scope.precision);
					$scope.displayValueB = $filter('number')(valueB, $scope.precision);;
					if ($scope.percentage === true) {
						$scope.displayValueA = ($filter('number')(100 * valueA, 2)) + "%";
						$scope.displayValueB = ($filter('number')(100 * valueB, 2)) + "%";						
					}
					$scope.shareA = 100 * calcValueA / (calcValueA + calcValueB);
					$scope.shareB = 100 * calcValueB / (calcValueA + calcValueB);

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
			  $scope.players.playerARanking = Player.get({player: $scope.players.playerA.toLowerCase()}, function() {
			    $scope.isPlayerALoading = false;
			  });
		  }
	  });
	  
	  $scope.$watch('players.playerB', function(newValue, oldValue) { 
		  if (newValue && PlayerService.isPlayerDefined(newValue)) {
			  console.log("Loading data player A");
			  $scope.isPlayerBLoading = true;
			  $scope.players.playerBRanking = Player.get({player: $scope.players.playerB.toLowerCase()}, function() {
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