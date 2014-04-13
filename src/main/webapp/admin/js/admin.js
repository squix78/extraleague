angular.module('Extraleague', ['ngResource', 'ngRoute', 'ngTouch', 'PlayerMappings', 'ui.bootstrap'])
    .config(function($routeProvider) {
        $routeProvider.when('/users', {
           controller : 'UserController',
           templateUrl : 'partials/users.html'
        })
        $routeProvider.when('/users/:player', {
        	controller : 'UserDetailController',
        	templateUrl : 'partials/userDetail.html'
        })
        .otherwise({
           redirectTo: '/users'
        });
    })
    .factory('PlayerUsers', ['$resource', function($resource) {
      return $resource('/rest/admin/playerUsers');
    }])
    .factory('PlayerUser', ['$resource', function($resource) {
    	return $resource('/rest/admin/playerUsers/:player');
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

function UserController($scope, $rootScope, $resource, $location, $routeParams, PlayerUsers) {
	$scope.loadPlayers = function() {
		$scope.isPlayersLoading = true;
		$scope.playerUsers = PlayerUsers.query({}, function() {
			$scope.isPlayersLoading = false;
		});
	}
	$scope.loadPlayers();
	$scope.add = function(){
		  var f = document.getElementById('file').files[0],
		      r = new FileReader();
		  r.onloadend = function(e){
		    var data = e.target.result;
		    var playerUsers = new PlayerUsers(angular.fromJson(data));
		    $scope.isPlayersSaving = true;
		    playerUsers.$save(function() {
		    	$scope.isPlayersSaving = false;
		    	$scope.loadPlayers();
		    });
		  }
		  r.readAsBinaryString(f);
		}

}
function UserDetailController($scope, $rootScope, $resource, $location, $routeParams, PlayerUser) {
	$scope.isPlayerLoading = false;
	$scope.isPlayerSaving = false;
	$scope.player = $routeParams.player;
	$scope.loadPlayer = function() {
		$scope.isPlayerLoading = true;
		$scope.playerUser = PlayerUser.get({player: $scope.player}, function() {
			$scope.isPlayerLoading = false;
		});
	}
	$scope.loadPlayer();
	
	$scope.saveUser = function() {
		$scope.isPlayerSaving = true;
		$scope.playerUser.$save({player: $scope.player}, function() {
			$scope.isPlayerSaving = false;
			$location.path("/users");
		});
	}

	
}
