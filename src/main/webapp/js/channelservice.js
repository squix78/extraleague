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
	
//    socket.onerror = onError;
//    socket.onclose = onClose;
     //that's where we connect
//    var socket = new SocketHandler();
//    socket.onMessage(function(data){
//     $rootScope.$apply(function () {
//    	 console.log('Received message: ' + data);
//      });
//    });

    return {
    	message: 1
    }
 }]);

var SocketHandler = function(){
	 this.messageCallback = function(){};

	 this.onMessage = function(callback){
	  var theCallback = function(message){
	   callback(JSON.parse(message.data));
	  }

	  if(this.channelSocket ==undefined){
	   this.messageCallback = theCallback;
	  }else{
	   this.channelSocket.onmessage = theCallback;
	  }
	 }

	 var context = this;
	 this.socketCreationCallback = function(channelData){
	        var channel = new goog.appengine.Channel(channelData.channelToken);
	        context.channelId = channelData.channelId;
	        var socket = channel.open();
	        socket.onerror = function(){
	            console.log("Channel error");
	        };
	        socket.onclose = function(){
	            console.log("Channel closed, reopening");
	            //We reopen the channel
	            context.messageCallback = context.channelSocket.onmessage;
	            context.channelSocket = undefined;
	            $.getJSON("chats/channel",context.socketCreationCallback);
	        };
	        context.channelSocket = socket;
	        console.log("Channel info received");
	        console.log(channelData.channelId);
	        context.channelSocket.onmessage = context.messageCallback;
	    };

	 $.getJSON("chats/channel",this.socketCreationCallback);
	}
