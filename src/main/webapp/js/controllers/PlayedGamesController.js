angular.module('Extraleague').controller('PlayedGamesController', 
function($scope, $rootScope, $resource, $timeout, $routeParams, $location, $filter, PlayedGames, Players) {
    $scope.isPlayedGamesLoading = true;
    PlayedGames.query(function (response) {  
        $scope.isPlayedGamesLoading = false;   
        $scope.playedGames = response;   
        $scope.players = [];
        $scope.playedGames.forEach(function(game) {  $scope.players = $scope.players.concat(game.players) });
        
        $scope.players = $scope.players.filter(function onlyUnique(value, index, self) { 
          return self.indexOf(value) === index;
        });                 
    });
});