angular.module("forCodeAdmin").controller("testeCtrl", function($scope, config, userService, $cookies){

	$scope.teste = $cookies.getObject('user');
	
});