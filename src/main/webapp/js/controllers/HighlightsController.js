angular.module('Extraleague').controller('HighlightsController', 
function($scope, Mutations) {
	$scope.isMutationsLoading = true;
	$scope.mutations = Mutations.query({}, function() {
		$scope.isMutationsLoading = false;
	})
});