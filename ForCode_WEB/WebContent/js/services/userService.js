angular.module("forCode").factory("userService", function($http, config, $cookies){

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
	
	var _getUserCookie = function(){
		return $cookie.user;
	}
	
	var _deleteUserCookie = function(){
		delete $cookie.user;
	}

	return {
		doLogin: _doLogin,
		registerContestant: _registerContestant,
		deleteUserCookie: _deleteUserCookie,
		getUserCookie: _getUserCookie		
	};

});