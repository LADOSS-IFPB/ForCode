angular.module("forCode").controller("loginCtrl", function($scope, userService, config){

	$scope.doLogin = function (user) {

		var _user = angular.copy(user);

		userService.doLogin(_user).success(function (data, status) {

			Materialize.toast(data.acessKey.key, 3000, 'rounded');

		}).error(function(data, status) {
				
			Materialize.toast(config.getError(data.code).message, 3000, 'rounded');
			delete user.password;
			
		});
	};
});