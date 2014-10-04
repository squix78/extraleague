angular.module('Extraleague').controller('AboutController', 
function($scope, $http) {
    var url = 'https://api.github.com/repos/squix78/extraleague/commits';
    $http.get(url).success(function(data) {
        $scope.commits = data;
    });
});