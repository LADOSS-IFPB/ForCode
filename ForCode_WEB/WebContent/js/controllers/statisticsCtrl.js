angular.module("forCodeContestant").controller("statisticsCtrl", function($scope, $cookies){

	$scope.user = $cookies.getObject('user');

});