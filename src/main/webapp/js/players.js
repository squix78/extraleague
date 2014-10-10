angular.module('PlayerMappings', [])
.factory('Players', ['$resource', function($resource) {
	return $resource('/rest/players/:player');
}])
.factory('PlayerUsers', ['$resource', function($resource) {
	return $resource('/rest/playerUsers');
}])
.factory('PlayerService', ['PlayerUsers', function(PlayerUsers) {
	var playerResultMap = {};
	var playerMap = [];
	var playerUsers = PlayerUsers.query({}, {get: {cache: true, method: 'GET' } });
	return {
		getPlayerPicture: function(shortname) {
			return playerUsers.$promise.then(function(result) {
				angular.forEach(playerUsers, function(value, key) {
					playerMap[value.player] = value;
				});				
				var playerUser = playerMap[shortname];
				if (angular.isDefined(playerUser) && shortname.length > 1) {
					console.log("Getting image address for " + playerUser.player);
					return "/playerImage?url=" + playerUser.imageUrl;
				} else {
					return "images/person2.png";
				}
			});
		}
	    
	 	
	}
}])
.directive('playerImg', ['PlayerService', function(PlayerService) {
    return {
    	template: '<div class="playerImage"><img class="player img img-rounded" ng-src="{{playerImgUrl}}"/></div>',
    	scope: {
    		player: "=",
    		team: "="
    	},
        link: function(scope, elem, attrs) {
        	scope.$watchCollection('[player, PlayerService.playerMap]', function(newValue, oldValues){
        		if (newValue) {
        			PlayerService.getPlayerPicture(scope.player).then(function(result) {
        				scope.playerImgUrl = result;
        			});
        		}
        	});
        }
    };
}])
.directive('player', ['PlayerService', function(PlayerService) {
    return {
    	template: '<div class="playerImage"><img class="player img img-rounded {{teamColor}}" ng-src="{{playerImgUrl}}"/><div class="caption">{{player}}</div></div>',
    	scope: {
    		player: "=",
    		team: "="
    	},
        link: function(scope, elem, attrs) {
        	scope.$watchCollection('[player, PlayerService.playerMap]', function(newValue, oldValues){
        		if (newValue) {
        			PlayerService.getPlayerPicture(scope.player).then(function(result) {
        				scope.playerImgUrl = result;
        			});
        			if (scope.team) {
        				scope.teamColor = scope.team + "TeamBorder";
        			}
        		}
        	});
        }
    };
}]);
