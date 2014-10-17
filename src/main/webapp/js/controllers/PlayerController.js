angular.module('Extraleague').controller('PlayerController', 
function PlayerController($scope, $rootScope, $routeParams, Player, TimeSeries, Badges) {
	  if (angular.isDefined($routeParams.player)) {
		  $scope.player = $routeParams.player;
	  }

	  $scope.isPlayerLoading = true;

	  $scope.$on('$viewContentLoaded', function() {
	    $rootScope.backlink = '/ranking';
	  });
	  
	  $rootScope.backlink = '/ranking';
	  
	  $scope.playerResult = Player.get({player: $scope.player}, function() {
	    $scope.isPlayerLoading = false;
	  });
	  
	  $scope.isTimeseriesLoading = true;
	  $scope.timeseries = TimeSeries.get({player: $scope.player}, function() {
	    $scope.isTimeseriesLoading = false;
	  });
	  
	  $scope.toolTipContentFunction = function(){
	    return function(key, x, y) {
	        return  '<h4>' + key + '</h4>' +
	              '<p>' +  y + ' at ' + x + '</p>'
	    };
	  };
	  $scope.xAxisTickFormatFunction = function(){
	    return function(d){
	      return d3.time.format('%y-%m-%d')(new Date(d)); //uncomment for date format
	    };
	  };
	  $scope.yAxisPercentFormatFunction = function(){
	    return function(d){
	      return d3.format(".0%")(d);
	    };
	  };
	  $scope.yAxisFormatFunction = function(){
		  return function(d){
			  return d; //uncomment for date format
		  };
	  };
	  $scope.badgeMap = Badges.get(function() {
	    $scope.badgeList = [];
	    angular.forEach($scope.badges, function(key, value) {
	      $scope.badgeList.push(key);
	    });
	  });
});