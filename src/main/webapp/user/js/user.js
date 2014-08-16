angular.module('Extraleague', ['ngResource', 'ngRoute', 'ngTouch', 'PlayerMappings', 'ui.bootstrap'])
    .config(function($routeProvider) {
        $routeProvider.when('/currentUser', {
           controller : 'UserController',
           templateUrl : 'partials/userDetail.html'
        })
        .when('/claimUser', {
        	controller : 'ClaimController',
        	templateUrl : 'partials/claimUser.html'
        })
        .otherwise({
           redirectTo: '/currentUser'
        });
    })
    .factory('CurrentUser', ['$resource', function($resource) {
    	return $resource('/rest/user/current');
    }])
    .factory('ClaimUser', ['$resource', function($resource) {
    	return $resource('/rest/user/claim/:player');
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


function UserController($scope, $rootScope, $resource, $location, $routeParams, CurrentUser) {
	$scope.isPlayerLoading = false;
	$scope.isPlayerSaving = false;
	$scope.loadPlayer = function() {
		$scope.isPlayerLoading = true;
		$scope.playerUser = CurrentUser.get({}, function() {
			console.log($scope.playerUser);
			$scope.isPlayerLoading = false;
			if (!angular.isDefined($scope.playerUser.player)) {
				console.log("found no user");
				$location.path("/claimUser");
			}
		});
	};
	$scope.loadPlayer();
	$scope.savePlayer = function() {
		$scope.isPlayerSaving = true;
		$scope.playerUser.$save({}, function() {
			$scope.isPlayerSaving = false;
			
		});
	}

	
}
function ClaimController($scope, $rootScope, $resource, $location, $routeParams, CurrentUser, ClaimUser) {
	$scope.claimUser = function() {
		$scope.isClaimLoading = true;
		$scope.claimedUser = ClaimUser.get({player: $scope.playerUser.player}, function() {
			$scope.isClaimLoading = false;
			$location.path("/currentUser");
		}, function(error) {
			$scope.isClaimLoading = false;
			$scope.errorMessage = error.data;
			console.log(error.data + ", " + error.status);
		})
	}
	
	
}
