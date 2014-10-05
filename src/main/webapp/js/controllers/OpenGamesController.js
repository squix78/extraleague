angular.module('Extraleague').controller('OpenGamesController', 
function($scope, $rootScope, $location, GameService) {
  $rootScope.backlink = false;
  $scope.updateGames = function() {
	  $scope.openGames = GameService.loadOpenGames();
  };
  
  $scope.updateGames();


  $scope.continueGame = function(game) {
      $location.path("/games/" + game.id);
  };
  $scope.watch = function(game) {
	  $location.path("/watch/" + game.table);
  }
  $scope.deleteGame = function(game) {
	  GameService.deleteGame(game);
  };

});