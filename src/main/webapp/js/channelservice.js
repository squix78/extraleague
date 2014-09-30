angular.module('gaeChannelService', ['ngResource'])
 .factory('NotificationToken', ['$resource', function($resource) {
     return $resource('/rest/notificationToken');
 }])

 .factory('NotificationService', ['$rootScope', '$timeout', 'NotificationToken', function($rootScope, $timeout, NotificationToken){
	
	var service = {
			
		initializeService: function() {
			
			var tokenObject = NotificationToken.get({}, function() {
				console.log("Token received");
				channel = new goog.appengine.Channel(tokenObject.token);
				socket = channel.open();
				socket.onopen = function() {
					console.log("Channel opened");
				};
				socket.onmessage = function(message) {
					console.log("Message received: " + message.data);
					var messageObject = angular.fromJson(message.data);
					$rootScope.$emit(messageObject.channel, messageObject);
				};
				socket.onerror = function(error) {
					console.log("Error in channel: " + error);
				};
				socket.onclose = function(info) {
					console.log("Channel closed: " + info);
					service.initializeService();
				};
			});
		} 
	}
	service.initializeService();
	$timeout(function() {
			service.initializeService();
	}, 1000 * 60 * 10); // reconnect the channel every 10 minutes
    return {
    }
 }]);
