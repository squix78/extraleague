angular.module('Extraleague').controller('SummaryController', 
function SummaryController($scope, $rootScope, $resource, $routeParams, $location, Summary, Match) {
  $scope.table = $routeParams.table;
  $scope.gameId = $routeParams.gameId;
  $rootScope.backlink = false;
  
  $scope.saveMatch = function(match) {
    $scope.isMatchSaving = true;
    match.$save({table: $scope.table, gameId: $scope.gameId}, function(match) {
      $scope.isMatchSaving = false;
      $scope.getSummary();
    });
  };
  $scope.matches = Match.query({table: $scope.table, gameId: $scope.gameId}, function() {

  });
  $scope.getSummary = function() {
    $scope.summary = Summary.get({table: $scope.table, gameId: $scope.gameId}, function() {
      
    });
  };
  $scope.getSummary();
  $scope.editScores = function () {
	  $location.path("/games/" + $scope.gameId);  
  };
  $scope.showChallengers = function() {
	  $location.path("/challenge/" + $scope.summary.table);
  }
});