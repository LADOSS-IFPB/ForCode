angular.module("forCodeAdmin").controller("testeCtrl", function($scope, config, userService){

	$scope.teste = userService.getUserCookie;
	
});