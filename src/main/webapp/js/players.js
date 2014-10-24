angular.module('PlayerMappings', [])
.factory('Players', ['$resource', function($resource) {
	return $resource('/rest/players/:player');
}])
.factory('PlayerUsers', ['$resource', function($resource) {
	return $resource('/rest/playerUsers');
}])
.factory('Blobs', ['$resource', function($resource) {
  return $resource('/rest/blobs');
}])
.factory('PlayerService', ['$rootScope', 'PlayerUsers', function($rootScope, PlayerUsers) {
	var playerResultMap = {};
	var playerMap = [];
	var playerUsers = undefined;
	var service = {
		getPlayerPicture: function(shortname) {
			return playerUsers.$promise.then(function(result) {
				var playerUser = playerMap[shortname];
				if (angular.isDefined(playerUser) && shortname.length > 1) {
					console.log("Getting image address for " + playerUser.player);
					return "/playerImage?url=" + playerUser.imageUrl;
				} else {
					return "/images/person2.png";
				}
			});
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
	$rootScope.$on("NewPlayerRegistered", function(event, message) {
		console.log("New player registered: " + message.player);
		service.loadPlayers();
	});
	return service;
}])
.directive('playerexists', ['PlayerService', function(PlayerService) {
  return {
    require: 'ngModel',
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$parsers.unshift(function(viewValue) {
    	viewValue = viewValue.toLowerCase();
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
.directive('playerCapture', ['$http', 'PlayerService', 'Blobs', function($http, PlayerService, Blobs) {
    return {
    	templateUrl: '/js/templates/capture.html',
    	scope: {
    	      imageUrl: '='
    	},
        link: function(scope, element, attrs) {

            //if(ngModel) { // Don't do anything unless we have a model
            	scope.blobUrl = Blobs.get({});
            	scope.enableCam = function() {
            		scope.showCam = true;
            	};
            	scope.enableUpload = function() {
            		scope.showUpload = true;
            	};
            	
            	scope.dataURItoBlob = function (dataURI) {
            		  // convert base64 to raw binary data held in a string
            		  // doesn't handle URLEncoded DataURIs - see SO answer #6850276 for code that does this
            		  var byteString = atob(dataURI.split(',')[1]);
            		  console.log("Byte String: " + byteString);
            		  // separate out the mime component
            		  var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

            		  // write the bytes of the string to an ArrayBuffer
            		  var ab = new ArrayBuffer(byteString.length);
            		  var ia = new Uint8Array(ab);
            		  for (var i = 0; i < byteString.length; i++) {
            		      ia[i] = byteString.charCodeAt(i);
            		  }

            		  // write the ArrayBuffer to a blob, and you're done
            		    try {
            		        return new Blob([ab], {type: mimeString});
            		    } catch (e) {
            		        // The BlobBuilder API has been deprecated in favour of Blob, but older
            		        // browsers don't know about the Blob constructor
            		        // IE10 also supports BlobBuilder, but since the `Blob` constructor
            		        //  also works, there's no need to add `MSBlobBuilder`.
            		        var BlobBuilder = window.WebKitBlobBuilder || window.MozBlobBuilder;
            		        var bb = new BlobBuilder();
            		        bb.append(ab);
            		        return bb.getBlob(mimeString);
            		    }
            	}
            	
            	scope.$watch('fileForUpload', function(media) {
            		if (angular.isDefined(media)) {
            			console.log("File ready for upload");
            			var formData = new FormData();
            		    formData.append('file', scope.fileForUpload);
            		    console.log("about to post to " + scope.blobUrl.url);
            		    scope.blobUrl = Blobs.get({}, function() {
            		    	scope.uploadFile(scope.blobUrl.url, formData);
            		    });
            		}
            	});
            	scope.$watch('media', function(media) {
                    if (angular.isDefined(media)) {
                    	scope.blobUrl = Blobs.get({}, function() {
                    		var blob = scope.dataURItoBlob(media);
                    		var formData = new FormData();
                    	    formData.append('file', blob);
                    	    console.log("about to post to " + scope.blobUrl.url);
                    	    scope.isCaptureLoading = true;
                    	    scope.uploadFile(scope.blobUrl.url, formData);

                    	});
                    }
                });
            	scope.hasGetUserMedia = function() {
            		  return !!(navigator.getUserMedia || navigator.webkitGetUserMedia ||
            		            navigator.mozGetUserMedia || navigator.msGetUserMedia);
            	};
            	scope.uploadFile = function(url, formData) {
            	    scope.isCaptureLoading = true;
            	    scope.showUpload = false;
            	    scope.showCam = false;
            	    $http.post(scope.blobUrl.url, formData, {
            	        transformRequest: angular.identity,
            	        headers: { 'Content-Type': undefined }
            	    })
            	    .success(function (response, status, c) {
            	    	console.log("Post worked");
                		scope.imageUrl = response;
                		scope.isCaptureLoading = false;
            	    })
                	.error(function (a, b, c) {
                		console.log("Post failed");
                		scope.isCaptureLoading = false;
                	});
            	};
            //}

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
					scope.playerImgUrl = "/images/person2.png";
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
}])
.directive('splitPlayers', function(PlayerService) {
  return { 
	restrict: 'A',
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
