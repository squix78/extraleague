angular.module('Extraleague').controller('StatsController', 
function($scope, $rootScope, $routeParams, Statistics) {
	$scope.isStatisticsLoading = true;
	$scope.statistics = Statistics.get({}, function() {
		$scope.isStatisticsLoading = false;
		console.log($scope.hourHistogram.length);		
		$scope.hourHistogram = [];
		angular.forEach($scope.statistics.hourHistogram, function(value, key) {
			console.log($scope.hourHistogram);
			$scope.hourHistogram.push(value);
			console.log($scope.hourHistogram.length);		
		});
	
	});
	
	$scope.hourHistogram = [{ "key": 0 , "value": 0.25}, { "key": 1 , "value": 0.75} ];

});