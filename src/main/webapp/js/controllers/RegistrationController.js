angular

.module('Extraleague').controller('RegistrationController', 
function($scope, $http, Blobs) {
	
	$scope.player = {};
	$scope.enableCam = function() {
		$scope.showCam = true;
	};
	
	$scope.$watch('media', function(media) {
        if (angular.isDefined(media)) {
        	$scope.blobUrl = Blobs.get({}, function() {
        		var formData = new FormData();
        	    formData.append('file', media);
        	    console.log("about to post to " + $scope.blobUrl.url);
//        	    $http.post($scope.blobUrl.url, formData, {
//        	        transformRequest: angular.identity,
//        	        headers: { 'Content-Type': undefined }
//        	    })
//        	    .success(function (a, b, c) {
//        	    	console.log("Post worked");
//        	    })
//	        	.error(function (a, b, c) {
//	        		console.log("Post failed");
//	        		
//	        	});
        	    $http({
        	        url: $scope.blobUrl.url,
        	        method: "POST",
        	        data: formData,
        	        headers: { 'Content-Type': undefined }
        	    })
        	    .then(function(response) {
        	    		console.log("Post worked");
        	        }, 
        	        function(response) { // optional
        	        	console.log("Post failed");
        	        }
        	    );
        	    }
        		$scope.showCam = false;
        		$scope.player.imageUrl = media;
        		
        	});
        }
    });
	
});