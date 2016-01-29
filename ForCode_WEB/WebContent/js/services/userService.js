angular.module("forCode").factory("userService", function($http, config){

	var _doLogin = function(user){
		
		var login = window.btoa(user.login);
		var password = window.btoa(user.password);

		return $http.post(config.baseUrl() + "/user/login?user=" + login, password)
	}

	var _registerContestant = function(user){

		user.password = window.btoa(user.password);
		user.typeUser = "br.edu.commons.forcode.entities.Contestant";

		return $http.post(config.baseUrl() + "/user/createcontestant", user)
	}

	return {
		doLogin: _doLogin,
		registerContestant: _registerContestant		
	};

});