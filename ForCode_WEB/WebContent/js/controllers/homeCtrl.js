angular.module("forCodeApp").controller("homeCtrl", function($scope, config, $cookies){

	$scope.user = $cookies.getObject('user');
	
	$scope.isContestant = function (){
		if ($scope.user.typeUser === "CONTESTANT")
			return true;
		else
			return false
	};

	$scope.isAdmin = function (){
		if ($scope.user.typeUser === "ADMIN")
			return true;
		else
			return false
	};

	$scope.isManager = function (){
		if ($scope.user.typeUser === "MANAGER")
			return true;
		else
			return false
	};

});