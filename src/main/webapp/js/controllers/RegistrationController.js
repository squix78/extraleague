angular.module('Extraleague').controller('RegistrationController', 
function($scope, $http, Blobs, PlayerService, PlayerUsers) {
	$scope.blobUrl = Blobs.get({});
	$scope.player = new PlayerUsers();
	$scope.isRegistrationOK = false;
	$scope.isRegistrationFailed = false;
	$scope.enableCam = function() {
		$scope.showCam = true;
	};
	$scope.enableUpload = function() {
		$scope.showUpload = true;
	};
	
	$scope.register = function() {
		$scope.isRegistrationFailed = false;
		$scope.isRegistrationOK = false;
		$scope.isPlayerRegistering = true;
		$scope.tmpPlayer = PlayerService.savePlayer($scope.player);
		$scope.tmpPlayer.then(function(result) {
			$scope.isPlayerRegistering = false;
			$scope.player = new PlayerUsers();
			$scope.isRegistrationOK = true;
		}, function(error) {
			console.log("Error saving player: " + error)
			$scope.isRegistrationFailed = true;
		});
	};
	
	$scope.dataURItoBlob = function (dataURI) {
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
	
	$scope.$watch('fileForUpload', function(media) {
		if (angular.isDefined(media)) {
			console.log("File ready for upload");
			var formData = new FormData();
		    formData.append('file', $scope.fileForUpload);
		    console.log("about to post to " + $scope.blobUrl.url);
		    $scope.blobUrl = Blobs.get({}, function() {
		    	$scope.uploadFile($scope.blobUrl.url, formData);
		    });
		}
	});
	$scope.$watch('media', function(media) {
        if (angular.isDefined(media)) {
        	$scope.blobUrl = Blobs.get({}, function() {
        		var blob = $scope.dataURItoBlob(media);
        		var formData = new FormData();
        	    formData.append('file', blob);
        	    console.log("about to post to " + $scope.blobUrl.url);
        	    $scope.isCaptureLoading = true;
        	    $scope.uploadFile($scope.blobUrl.url, formData);

        	});
        }
    });
	$scope.hasGetUserMedia = function() {
		  return !!(navigator.getUserMedia || navigator.webkitGetUserMedia ||
		            navigator.mozGetUserMedia || navigator.msGetUserMedia);
	}
	
	$scope.uploadFile = function(url, formData) {
	    $scope.isCaptureLoading = true;
	    $scope.showUpload = false;
	    $scope.showCam = false;
	    $http.post($scope.blobUrl.url, formData, {
	        transformRequest: angular.identity,
	        headers: { 'Content-Type': undefined }
	    })
	    .success(function (response, status, c) {
	    	console.log("Post worked");
    		$scope.player.imageUrl = response;
    		$scope.isCaptureLoading = false;
	    })
    	.error(function (a, b, c) {
    		console.log("Post failed");
    		$scope.isCaptureLoading = false;
    	});
	};
	
});