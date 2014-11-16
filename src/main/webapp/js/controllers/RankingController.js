angular.module('Extraleague')

.controller('RankingController', 
function($scope, $rootScope, $resource, $routeParams, $location, Ranking, Badges, Tables, NotificationService) {
  
  $scope.predicate = [ '-eloValue', '-successRate'];
  $scope.isRankingLoading = true;
  $rootScope.backlink = false;
  $scope.tag = $routeParams.tag;
  $scope.type = $routeParams.type || "All";
  $scope.updateRankings = function() {
	  $scope.rankings = Ranking.get({tag: $scope.tag, type: $scope.type}, function() {
	    $scope.isRankingLoading = false;
	    
	  });
  };
  $scope.updateRankings();
  $scope.tables = Tables.query();
  // extract this to a service to avoid duplication and provide
	// caching
  $scope.badgeMap = Badges.get(function() {
    $scope.badgeList = [];
    angular.forEach($scope.badgeMap, function(key, value) {
      $scope.badgeList.push(key);
    });
  });
  $scope.openRankingByType = function(type) {
	  $location.path("/ranking/" + type);
  }
  $scope.openRankingByTag = function(tag) {
	  $location.path("/ranking/tag/" + tag);
  }
  
  $rootScope.$on("UpdateRankings", function(event, message) {
	    console.log("Received ranking update from server");
	    $scope.updateRankings();
        if(!$scope.$$phase) {
	       $scope.$apply();
	    }
  });
});