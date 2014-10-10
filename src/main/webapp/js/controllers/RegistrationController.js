angular.module('Extraleague').controller('RegistrationController', 
function($scope) {
	
	$scope.$watch('media', function(media) {
        console.log(media);
    });
	
});