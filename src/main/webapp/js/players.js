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
	var playerUsers = undefined;
	var service = {
		getPlayerPicture: function(shortname) {
				var playerUser = playerMap[shortname];
				if (angular.isDefined(playerUser) && shortname.length > 1) {
					console.log("Getting image address for " + playerUser.player);
					return "/playerImage?url=" + playerUser.imageUrl;
				} else {
					return "images/person2.png";
				}
		},
		loadPlayers: function() {
			playerUsers = PlayerUsers.query({}, {get: {cache: true, method: 'GET' } });
			playerUsers.$promise.then(function(result) {
				angular.forEach(playerUsers, function(value, key) {
					playerMap[value.player] = value;
				});		
			});
		},
		savePlayer: function(player) {
			var result = player.$save({});
			result.then(function() {
				service.loadPlayers();
			}, function() {
				console.log("Saving failed");
			});
			return result;
		},
		isPlayerDefined: function(player) {
			if (angular.isDefined(player)) {
				return angular.isDefined(playerMap[player.toLowerCase()]);
			}
		}
	    
	 	
	}
	service.loadPlayers();
	return service;
}])
.directive('playerexists', ['PlayerService', function(PlayerService) {
  return {
    require: 'ngModel',
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$parsers.unshift(function(viewValue) {
        if (PlayerService.isPlayerDefined(viewValue)) {
          // it is valid
          ctrl.$setValidity('playerexists', false);
          return viewValue;
        } else {
          // it is invalid, return undefined (no model update)
          ctrl.$setValidity('playerexists', true);
          return viewValue;
        }
      });
    }
  };
}])
.directive('playersexistnot', ['PlayerService', function(PlayerService) {
	return {
		require: 'ngModel',
		link: function(scope, elm, attrs, ctrl) {
			ctrl.$parsers.unshift(function(viewValue) {
				var players = viewValue.toLowerCase().replace(/,/g,'').split(' ');
				angular.forEach(players, function(player){
					if (PlayerService.isPlayerDefined(player)) {
						// it is valid
						ctrl.$setValidity('playersexistnot', true);
						return viewValue;
					} else {
						// it is invalid, return undefined (no model update)
						ctrl.$setValidity('playersexistnot', false);
						return viewValue;
					}
				});
			});
		}
	};
}])
.directive('playerexistsnot', ['PlayerService', function(PlayerService) {
	return {
		require: 'ngModel',
		link: function(scope, elm, attrs, ctrl) {
			ctrl.$parsers.unshift(function(viewValue) {
				if (PlayerService.isPlayerDefined(viewValue)) {
					// it is valid
					ctrl.$setValidity('playerexistsnot', true);
					return viewValue;
				} else {
					// it is invalid, return undefined (no model update)
					ctrl.$setValidity('playerexistsnot', false);
					return viewValue;
				}
			});
		}
	};
}])
.directive('playerImg', ['PlayerService', function(PlayerService) {
    return {
    	template: '<div class="playerImage"><img class="player img img-rounded" ng-src="{{playerImgUrl}}"/></div>',
    	scope: {
    		playerImg: "="
    	},
        link: function(scope, elem, attrs) {
        	scope.$watch('playerImg', function(newValue, oldValue) {
    				if (angular.isDefined(newValue)) {
    					scope.playerImgUrl = "/playerImage?url=" + newValue;
    					//scope.playerImgUrl = newValue;
    				} else {
    					scope.playerImgUrl = "images/person2.png";
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
        			result = PlayerService.getPlayerPicture(scope.player);
        			scope.playerImgUrl = result;
        			
        			if (scope.team) {
        				scope.teamColor = scope.team + "TeamBorder";
        			}
        		}
        	});
        }
    };
}])
.directive('splitPlayers', function(PlayerService) {
  return { restrict: 'A',
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {

      if(ngModel) { // Don't do anything unless we have a model

        ngModel.$parsers.push(function (value) {
	          if (angular.isDefined(value)) {
	        	  
		          var players = value.toLowerCase().replace(/,/g,'').split(' ');
		          var isValid = true;
		          angular.forEach(players, function(player) {
		        	  if (!PlayerService.isPlayerDefined(player)) {
		        		  isValid = false;
		        	  }
		          });
		          ngModel.$setValidity('playerunknown', isValid);
		          var isCorrectNumber = angular.isDefined(attrs.playersExpected) && attrs.playersExpected == players.length;
		          ngModel.$setValidity('playersexpected', isCorrectNumber);
		          if (angular.isDefined(attrs.noArray)) {
		        	  return players[0];
		          }
		          return players;
	          }
        });
        
        ngModel.$formatters.push(function (value) {
            return value;
        });

      }
    }
  };
});;
