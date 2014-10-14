angular.module('Extraleague').controller('NewGameController', 
function($scope, $rootScope, $resource, $routeParams, $location, Games, Game, Players, Tables, GameModes, GamePreview) {
   $scope.isSavingGame = false;
   $scope.game = new Games();
   
   $scope.isTablesLoading = true;
   $scope.tables = Tables.query({}, function() {
		  $scope.isTablesLoading = false;
		  $scope.game.table = $scope.tables[0].name;
   });
   
   $scope.isModesLoading = true;
   $scope.modes = GameModes.query({}, function() {
	  $scope.isModesLoading = false;
	  $scope.game.gameMode = $scope.modes[0].name;
   });
   
   $scope.isGameComplete = function() {
	   var game = $scope.game;
	   return angular.isDefined(game.table)
	   	&& angular.isDefined(game.gameMode)
	   	&& angular.isDefined(game.players)
	   	&& game.players.length == 4;
   }

   $scope.$on('$viewContentLoaded', function() {
     $rootScope.backlink = '/tables';
   });

   $scope.$watch('game', function(newValue, oldValue) {
	   console.log("Change in game detected");
       if ($scope.isGameComplete()) {
     	  $scope.loadPreview();
       }
   }, true);
   
   $scope.loadPreview = function() {
	   console.log("Loading preview...");
	   var preview = new GamePreview($scope.game);
	   $scope.preview = GamePreview.save({}, $scope.game, function() {
		  console.log($scope.preview); 
	   });
   }
  
  $scope.startGame = function() {
	  $scope.isGameStarting = true;
	  $scope.game.$save(function(savedGame) {
		  $scope.isGameStarting = false;
		  $location.path("/games/" + savedGame.id);
	  });
  };
  
  $scope.continueGame = function(gameId) {
    $location.path("/games/" + gameId);
  };
  
  $scope.deleteGame = function(gameId) {
    Game.remove({table: $scope.table, gameId: gameId});
    $scope.updateGames();
  };
  
  var playersResult = Players.get({}, function() {
    $scope.players = playersResult;
  });
});