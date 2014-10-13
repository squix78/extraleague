angular.module('Extraleague').controller('MeetingPointController', 
function ($scope, $rootScope, $timeout, $location, MeetingPointPlayers, 
		MeetingPointPlayer, Tables, GameModes, Games, NotificationService, PlayerService) {
	
	$scope.game = new Games();
	$scope.loadPlayers = function() {
		$scope.arePlayersLoading = true;
		$scope.players = MeetingPointPlayers.query({}, function() {
			$scope.arePlayersLoading = false;
		});
	};
	$scope.loadPlayers();
	$scope.isTablesLoading = true;
    $scope.tables = Tables.query({}, function() {
	    $scope.isTablesLoading = false;
    });
    $scope.isModesLoading = true;
    $scope.modes = GameModes.query({}, function() {
 	  $scope.isModesLoading = false;
    });
    $scope.availableTimes = [
       {label: 'next 15min', timeDiff: '15'},
       {label: 'next 30min', timeDiff: '30'},
       {label: 'next 1h', timeDiff: '60'},
       {label: 'next 2h', timeDiff: '120'}                   
    ];
    $scope.addPlayer = function() {
    	var availableUntil = new Date().getTime();
    	availableUntil += $scope.availableNextMin * 60 * 1000;
    	$scope.player.availableUntil = new Date(availableUntil);
    	$scope.player.player = $scope.player.player.toLowerCase();
    	var player = new MeetingPointPlayers($scope.player);
    	$scope.player = "";
    	player.$save({}, function() {
    		$scope.loadPlayers();
    	});
    };
    $scope.togglePlayer = function(player) {
    	player.enabled = ! (player.enabled | false);
    	console.log("Player: " + player.player + ", enabled: " +player.enabled );
    	$scope.game.players = [];
    	angular.forEach($scope.getEnabledPlayers(), function(player, index) {
    		$scope.game.players.push(player.player);
    	});

    }
    $scope.startGame = function() {
	    $scope.game.$save({}, function(savedGame){
	        $location.path("/games/" + savedGame.id);      
	    });
    }
    $scope.countEnabledPlayers = function() {
    	return $scope.getEnabledPlayers().length;
    }
    $scope.isGameComplete = function() {
 	   var game = $scope.game;
 	   return angular.isDefined(game.table)
 	   	&& angular.isDefined(game.gameMode)
 	   	&& angular.isDefined(game.players)
 	   	&& game.players.length == 4;
    }
    
    $scope.getEnabledPlayers = function() {
    	var enabledPlayers = [];
    	var now = new Date();
    	angular.forEach($scope.players, function(player) {
    		if (player.enabled && player.availableUntil > now) {
    			enabledPlayers.push(player);
    		}
    	});
    	return enabledPlayers;
    }
    $scope.refreshFilter = function() {
    	if(!$scope.$$phase) {
    		$scope.$apply();
    	}
		var timer = $timeout($scope.refreshFilter, 10000);
		$scope.$on('$locationChangeStart', function(){
			$timeout.cancel(timer);
		});
    }
    $scope.refreshFilter();
    $scope.filterOldEntries = function(element) {
    	var now = new Date();
    	return element.availableUntil > now;
    };
    $scope.removePlayer = function(player) {
    	var playerToDelete = new MeetingPointPlayer(player);
    	playerToDelete.$delete({playerId: player.id}, function() {
    		$scope.loadPlayers();
    	});
    }
    
	$rootScope.$on("UpdateMeetingPoint", function(event, message) {
		   console.log("Received update for meeting point");
		   var previousPlayers = $scope.players;
		   
		   $scope.players = message.players;
		   
		   angular.forEach(previousPlayers, function(previousPlayer) {
			  angular.forEach($scope.players, function(player) {
				 if (previousPlayer.id === player.id) {
					 player.enabled = previousPlayer.enabled;
				 } 
			  });
		   });
	       if(!$scope.$$phase) {
	         $scope.$apply();
	       }
	});

});