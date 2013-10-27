angular
    .module('JazoonVote', ['ngResource', 'ui.bootstrap', 'ngSanitize', 'ui.bootstrap.rating'])
    .config(function($routeProvider) {
      $routeProvider.when('/submissions?topic=:topic', {
        controller : 'MainController',
        templateUrl : 'partials/submissions.html'
      }).when('/submissions', {
        controller : 'MainController',
        templateUrl : 'partials/submissions.html'
      }).when('/submissions/:paperID', {
        controller : 'SubmissionController',
        templateUrl : 'partials/submissionDetail.html'
      }).when('/agenda/:dateParam', {
    	  controller : 'ProgramController',
    	  templateUrl : 'partials/program.html'
      }).when('/now', {
    	  controller : 'NowController',
    	  templateUrl : 'partials/now.html'
      }).when('/nextInRoom/:selectedRoom', {
        controller : 'NextInRoomController',
        templateUrl : 'partials/nextInRoom.html'
      }).when('/infoscreen', {
    	  controller : 'InfoScreenController',
    	  templateUrl : 'partials/infoscreen.html'
      }).when('/twitterwall', {
    	  controller : 'TwitterWallController',
    	  templateUrl : 'partials/twitterwall.html'
      }).when('/speakers', {
    	  controller : 'SpeakerController',
    	  templateUrl : 'partials/speakers.html'
      }).when('/admin/highscore', {
    	  controller : 'AdminHighscoreController',
    	  templateUrl : 'partials/highscore.html'
      }).when('/admin/stats', {
    	  controller : 'AdminStatsController',
    	  templateUrl : 'partials/stats.html'
      }).otherwise({
          controller : 'ProgramController',
          templateUrl : 'partials/program.html'
      });
    });