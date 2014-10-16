angular.module('Extraleague').controller('WatcherController', 
function($scope, $routeParams, GameService) {
	$scope.table = $routeParams.table;
	
    $scope.updateGames = function() {
	  $scope.openGames = GameService.loadOpenGames();
    };
  
    $scope.updateGames();
    
    $scope.getScorers = function(team, goals) {
    	var goalText = "";
    	angular.forEach(goals, function(goal, key) {
    		var scorer = goal.scorer;
    		if (scorer === team[0] || scorer === team[1]) {
    			goalText += scorer + "<BR/>";
    		}
    	});
    	return goalText;
    }
    
    $scope.getBiggerArray = function(n, m) {
    	return new Array(Math.max(n, m));
    }
    
    $scope.range = function(n) {
        return new Array(n);
    };
	
});