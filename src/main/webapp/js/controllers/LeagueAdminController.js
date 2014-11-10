angular.module('Extraleague')
.factory('League', ['$resource', function($resource) {
	return $resource('/rest/leagueAdmin/league');
}])
.controller('LeagueAdminController', 
function LeagueAdminController($scope, $rootScope, $resource, $location, $routeParams, League) {
	$scope.isLoadingLeague = true;
	$scope.league = League.get({}, function() {
		$scope.isLoadingLeague = false;
	});
	
	$scope.saveLeague = function() {
		$scope.isSavingLeague = true;
		$scope.league.$save({}, function() {
			$scope.isSavingLeague = false;
		});
	}
	$scope.addTable = function() {
		$scope.league.tables.push("");
	}
	$scope.removeTable = function(index) {
		$scope.league.tables.splice(index, 1);
	}
});