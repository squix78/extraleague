angular.module('Extraleague').controller('RankingByTagController', 
function($scope, $rootScope, $resource, $routeParams, $location, RankingByTag, Badges, Tables) {
	$scope.tag = $routeParams.tag;
	$scope.predicate = [ '-eloValue', '-successRate'];
	$scope.isRankingLoading = true;
	$rootScope.backlink = false;
	$scope.rankings = RankingByTag.query({tag: $scope.tag}, function() {
		$scope.isRankingLoading = false;
		
	});
	
	$scope.tables = Tables.query();
	// extract this to a service to avoid duplication and provide caching
	$scope.badgeMap = Badges.get(function() {
		$scope.badgeList = [];
		angular.forEach($scope.badgeMap, function(key, value) {
			$scope.badgeList.push(key);
		});
	});
	  $scope.openRankingByTag = function(tag) {
		  if (tag !== "") {
			  $location.path("/ranking/tag/" + tag);		  
		  } else {
			  $location.path("/ranking");		  
		  }
	  }
});