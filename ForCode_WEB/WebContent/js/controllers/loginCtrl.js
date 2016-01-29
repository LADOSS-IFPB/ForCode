angular.module("forCode").controller("loginCtrl", function($scope, userService, config, $cookies){

	$scope.doLogin = function (user) {

		var _user = angular.copy(user);

		userService.doLogin(_user).success(function (data, status) {
			
			$cookies.putObject('user', data);
			
			if (data.typeUser === 'ADMIN')
				window.location="webapp/admin/index.html";
			
			if (data.typeUser === 'MANAGER')
				window.location="webapp/manager/index.html";

			if (data.typeUser === 'CONTESTANT')
				window.location="webapp/contestant/index.html";

		}).error(function(data, status) {
				
			Materialize.toast(config.getError(data.code).message, 3000, 'rounded');
			delete user.password;
			
		});
	};
});