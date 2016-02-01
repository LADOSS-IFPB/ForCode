angular.module("forCode").controller("loginCtrl", function($scope, userService, config, $cookies){

	$scope.doLogin = function (user) {

		var _user = angular.copy(user);

		userService.doLogin(_user).success(function (data, status) {
			
			$cookies.putObject('user', data);
			
			
			window.location="webapp/home.html";

		}).error(function(data, status) {
				
			Materialize.toast(config.getError(data.code).message, 3000, 'rounded');
			delete user.password;
			
		});
	};
});