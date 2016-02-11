angular.module("forCodeConfig").controller("loginCtrl", function($scope, userService, config, $cookies, typeUserUtilService){

	$scope.doLogin = function (user) {

		var _user = angular.copy(user);

		userService.doLogin(_user).success(function (data, status) {
			
			$cookies.putObject('user', data);
			
			if (typeUserUtilService.isManager()) {
				window.location="webapp/manager/";
			}

			if (typeUserUtilService.isContestant()) {
				window.location="webapp/contestant/";
			}

			if (typeUserUtilService.isAdmin()) {
				window.location="webapp/admin/";
			}

		}).error(function(data, status) {
				
			Materialize.toast(config.getError(data.code).message, 3000, 'rounded');
			delete user.password;
			
		});
	};

	$scope.doLogout = function () {

		var _user = angular.copy($cookies.getObject('user'));

		if (typeUserUtilService.isManager()) {
			_user.typeUser = "br.edu.commons.forcode.entities.Manager";
		}

		if (typeUserUtilService.isContestant()) {
			_user.typeUser = "br.edu.commons.forcode.entities.Contestant";
		}

		if (typeUserUtilService.isAdmin()) {
			_user.typeUser = "br.edu.commons.forcode.entities.Admin";
		}

		//userService.doLogout(_user).success(function (data, status) {
			
			delete _user;
			$cookies.remove('user', {path: '/'});

			window.location = "../../";

		//}).error(function(data, status) {
				
			//Materialize.toast(config.getError(data.code).message, 3000, 'rounded');
			
		//});
	};

});