angular.module('Extraleague').controller('MainController', 
function($scope, $rootScope, $resource, $location, $routeParams, League, CurrentUser) {
  
  $rootScope.backlink = false;
  
  $rootScope.backAction = function() {
    $location.path($rootScope.backlink);
  }; 
  
  $scope.navigateTo = function(target) {
	$scope.navCollapsed = true
	$location.path(target);  
  };
  
  $scope.isCurrentUserLoading = true;
  $scope.currentUser = CurrentUser.get({}, function() {
	  $scope.isCurrentUserLoading = false;
	  console.log($scope.currentUser);
  });
  $scope.league = League.get({}, function() {
	  
  });

});