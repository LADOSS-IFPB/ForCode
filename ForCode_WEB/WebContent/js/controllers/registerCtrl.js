angular.module("forCode").controller("registerCtrl", function($scope, $location, config, userService, institutions){

	$scope.passwordVerify = function(){
		if($scope.user.password != null && $scope.user.password === $scope.repeat_password)
			return true;
		else
			return false;
	};

	$scope.errorMessage = function(field, message){

		console.log(field.$name);

		if(field.$error.required)
			return "";
		else
			return typeof(message) === "undefined"? "Inválido" : message;
	};

	$scope.institutions = institutions.data;

	$scope.doRegister = function(user){

		var _user = angular.copy(user);

		userService.registerContestant(_user).success(function (data, status) {

			$location.path("/home");
			Materialize.toast("Usuário criado com sucesso", 3000, 'rounded');

		}).error(function(data, status) {
				
			Materialize.toast(config.getError(data.code).message, 3000, 'rounded');
		
		});
	};
})