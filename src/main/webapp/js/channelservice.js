angular.module('gaeChannelService', ['ngResource'])
 .factory('NotificationToken', ['$resource', function($resource) {
     return $resource('/rest/notificationToken');
 }])

 .factory('NotificationService', ['$rootScope', 'NotificationToken', function($rootScope, NotificationToken){
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
	});

    return {
    }
 }]);
