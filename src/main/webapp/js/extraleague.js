angular.module('Extraleague', ['ngResource', 'ngRoute', 'ngTouch', 'PlayerMappings', 'Charts', 'Games', 
                               'ui.bootstrap', 'ui.bootstrap.buttons', 'ui.bootstrap.alert', 'ui.bootstrap.modal', 
                               'nvd3ChartDirectives', 'omr.directives', 'gaeChannelService', 
                               'angulartics', 'angulartics.google.analytics', 'squix.services.logging', 'remoteValidation'])
    .config(function($routeProvider) {
        $routeProvider
        .when('/newGame', {
        	controller : 'NewGameController',
        	templateUrl : 'partials/newGame.html'
        })
        .when('/games/:gameId', {
           controller : 'GameController',
           templateUrl : 'partials/game.html'
        })
        .when('/games/:gameId/summary', {
           controller : 'SummaryController',
           templateUrl : 'partials/summary.html'
        })
        .when('/openGames', {
           controller : 'OpenGamesController',
           templateUrl : 'partials/openGames.html'
        })
         .when('/playedGames', {
           controller : 'PlayedGamesController',
           templateUrl : 'partials/playedGames.html'
        })
        .when('/stats', {
        	controller : 'StatsController',
        	templateUrl : 'partials/stats.html'
        })
        .when('/ranking/:type?/:tag?', {
        	controller : 'RankingController',
        	templateUrl : 'partials/ranking.html'
        })
        .when('/player/:player', {
           controller : 'PlayerController',
           templateUrl : 'partials/player.html'
        })
        .when('/headToHead', {
        	controller : 'HeadToHeadController',
        	templateUrl : 'partials/headToHead.html'
        })
        .when('/highlights', {
        	controller : 'HighlightsController',
        	templateUrl : 'partials/highlights.html'
        })
        .when('/meetingPoint', {
        	controller : 'MeetingPointController',
        	templateUrl : 'partials/meetingpoint.html'
        })
        .when('/watch/:table', {
        	controller : 'WatcherController',
        	templateUrl : 'partials/watch.html'
        })
        .when('/challenge/:table', {
        	controller : 'ChallengeController',
        	templateUrl : 'partials/challengers.html'
        })
        .when('/about', {
           controller : 'AboutController',
           templateUrl : 'partials/about.html'
        })
        .when('/getExtraleague', {
        	controller : 'GetExtraleagueController',
        	templateUrl : 'partials/getExtraleague.html'
        })
        .when('/account', {
           controller : 'UserController',
           templateUrl : 'partials/userDetail.html'
        })
        .when('/playerStats', {
        	controller : 'PlayerStatsController',
        	templateUrl : 'partials/playerStats.html'
        })
        .when('/admin', {
        	controller : 'LeagueAdminController',
        	templateUrl : 'partials/league.html'
        })
        .when('/registration', {
        	controller : 'RegistrationController',
        	templateUrl : 'partials/registration.html'
        })
        .otherwise({
           redirectTo: '/highlights'
        });
    })
    .config(['$httpProvider', function($httpProvider) {
	
		$httpProvider.responseInterceptors.push(['$q', function($q) {
	
	
			return function(promise) {
				return promise.then(function(response) {
					console.log("Response status: " + response.status);
	
					return response; 
	
				}, function(response) {
					console.log("Response status: " + response.status);
					if (response.status === 401) {
						
//						response.data = { 
//					 		status: false, 
//					 		description: 'Authentication required!'
//					 	};
	
						return response;
	
					}
					return $q.reject(response);
	
				});
	
			}
	
		}]);
	
	}])
    .factory('Ping', ['$resource', function($resource) {
      return $resource('/rest/ping');
    }])
    .factory('Tables', ['$resource', function($resource) {
      return $resource('/rest/tables');
    }])
    .factory('GameModes', ['$resource', function($resource) {
    	return $resource('/rest/modes');
    }])
    .factory('GamePreview', ['$resource', function($resource) {
    	return $resource('/rest/gamePreview');
    }])
    .factory('Summary', ['$resource', function($resource) {
      return $resource('/rest/games/:gameId/summary');
    }])
    .factory('Ranking', ['$resource', function($resource) {
      return $resource('/rest/ranking');
    }])
    .factory('RankingByTag', ['$resource', function($resource) {
    	return $resource('/rest/rankings/tags/:tag');
    }])
    .factory('Player', ['$resource', function($resource) {
      return $resource('/rest/players/:player');
    }])
    .factory('Players', ['$resource', function($resource) {
      return $resource('/rest/players');
    }])
    .factory('MeetingPointPlayers', ['$resource', function($resource) {
    	return $resource('/rest/meetingPointPlayers');
    }])
    .factory('MeetingPointPlayer', ['$resource', function($resource) {
    	return $resource('/rest/meetingPointPlayers/:playerId');
    }])
    .factory('TimeSeries', ['$resource', function($resource) {
      return $resource('/rest/timeseries/:player');
    }])
    .factory('Statistics', ['$resource', function($resource) {
    	return $resource('/rest/statistics');
    }])
    .factory('Badges', ['$resource', function($resource) {
        return $resource('/rest/badges', {}, {get: {cache: true, method: 'GET' } } );
    }])
    .factory('SpecialEvents', ['$resource', function($resource) {
    	return $resource('/rest/specialEvents', {}, {get: {cache: true, method: 'GET' } } );
    }])
    .factory('Mutations', ['$resource', function($resource) {
    	return $resource('/rest/mutations');
    }])
    .factory('CurrentUser', ['$resource', function($resource) {
    	return $resource('/rest/currentUser');
    }])
    .factory('PlayerStats', ['$resource', function($resource) {
    	return $resource('/rest/currentPlayerStats');
    }])
    .factory('League', ['$resource', function($resource) {
    	return $resource('/rest/league');
    }])
    .factory('ChallengerTeam', ['$resource', function($resource) {
    	return $resource('/rest/challengers/:table/:id');
    }])
    .factory('WinnerTeam', ['$resource', function($resource) {
    	return $resource('/rest/winners/:table');
    }])
    .factory('$exceptionHandler', ['$log', '$window', 'traceService', function ($log, $window, traceService) {
	  return function (exception, cause) {
		$log.error.apply($log, arguments);
		try{
			var error = {};
			error.stackTrace = traceService.print({e: exception});
			error.message = exception.toString();
			error.url = $window.location.href;
			error.cause = cause;
			error.userAgent = $window.navigator.userAgent;
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.open("POST","/rest/error",true);
			xmlhttp.setRequestHeader("Content-type", "application/json");
			xmlhttp.send(JSON.stringify(error));
        } catch (loggingError){
            $log.warn("Error server-side logging failed");
            $log.log(loggingError);
        }
	  };
	}])
    .directive('badges', function() {
      return {
        restrict: 'A',
        require: '^ngModel',
        templateUrl: 'js/templates/badges.html',
          scope: {
              ngModel: '=',
              badgeMap: '='
          }
      };
    })
    .directive('delta', function() {
    	return {
    		restrict: 'A',
    		require: '^ngModel',
    		templateUrl: 'js/templates/delta.html',
    		scope: {
    			value: '=ngModel',
    		},
    		link: function(scope, element, attrs, ngModel) {
    		}
    	};
    })
    .directive('highlightBadges', ['Badges', 'SpecialEvents', function(Badges, SpecialEvents) {
	  return {    
	    restrict: 'A',    
	    require: 'ngModel',
	    replace: true,   
	    scope: { ngModel: '=ngModel' },
	    link: function compile(scope, element, attrs, controller) {         
	        scope.$watch('ngModel', function(value) { 
	        	var badgeMap = Badges.get({}, function() {
	        		
		        	if (angular.isDefined(value) && typeof value === 'string') {
			        	angular.forEach(badgeMap, function(badge, name) {
				            angular.forEach(value.match(badge.jsRegex), function(word) {
				            	if (angular.isDefined(badge) && angular.isDefined(badge.badgeType)) {
				            		value = value.replace(word, 
				            				'<span class="label label-primary ' 
				            				+ badge.badgeType + '" title="' 
				            				+ badge.description + '"> <i class="fa ' 
				            				+ badge.faClass + '"></i> ' + word + '</span>');
				            	}
				            });
				            element.html(value); 
			        	});
		        	}
	        	});

				var specialEvents = SpecialEvents.get({}, function() {
					
					if (angular.isDefined(value) && typeof value === 'string') {
						angular.forEach(specialEvents.eventMap, function(specialEvent, name) {
							angular.forEach(value.match(specialEvent.name), function(word) {
								if (angular.isDefined(specialEvent)) {
									value = value.replace(word, 
											'<span class="btn btn-xs ' 
											+ specialEvent.buttonClass + '" title="' 
											+ specialEvent.description + '"> <i class=" ' 
											+ specialEvent.iconClass + '"></i> ' + word + '</span>');
								}
							});
							element.html(value); 
						});
					}
				});
			}); 
	    }
	  };  
	}])
	.directive('ranking', [ function() {
		return {
	    	templateUrl: '/js/templates/rankingValue.html',
			scope: {
				name: "=",
				ranking: "=",
				precision: "=?",
				percentage: "=?"
			},
			compile: function(element, attrs){
			       if (!attrs.precision) { attrs.precision = 0; }
			       if (!attrs.percentage) { 
			    	   attrs.percentage = false;
			       } 

			},
			controller: function($scope) {
			       if ($scope.percentage) {
			    	   $scope.factor = 100;
			    	   if (!angular.isDefined($scope.precision)) {
			    		   $scope.precision = 2;
			    	   }
			       } else {
			    	   $scope.factor = 1;
			    	   if (!angular.isDefined($scope.precision)) {
			    		   $scope.precision = 0;
			    	   }
			       }
			},
			link: function(scope, elem, attrs) {
			}
		};
	}])
	.directive('extraleagueNavbar', function ($location) {
	  return {
	    restrict: 'A',
        link: function link(scope, element) {
          var listener = function () {
            return $location.path();
          };
          var changeHandler = function (newValue) {
            $('li[data-match-route]', element).each(function (k, li) {
              var $li = angular.element(li);
              var pattern = $li.attr('data-match-route');
              var regexp = new RegExp('^' + pattern + '$', 'i');
              if (regexp.test(newValue)) {
                $li.addClass('active');
              } else {
                $li.removeClass('active');
              }
		    });
		  };
          scope.$watch(listener, changeHandler);
      	}
	  };
  	});











