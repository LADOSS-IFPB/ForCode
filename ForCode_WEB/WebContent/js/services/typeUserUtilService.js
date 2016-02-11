angular.module("forCodeConfig").factory("typeUserUtilService", function(config, $cookies){
	
	var _isContestant = function (){

		var _user = $cookies.getObject('user');

		if (_user.typeUser === "CONTESTANT")
			return true;
		else
			return false
	};

	var _isAdmin = function (){

		var _user = $cookies.getObject('user');

		if (_user.typeUser === "ADMIN")
			return true;
		else
			return false
	};

	var _isManager = function (){

		var _user = $cookies.getObject('user');
		
		if (_user.typeUser === "MANAGER")
			return true;
		else
			return false
	};

	return {
		isContestant: _isContestant,
		isAdmin: _isAdmin,
		isManager: _isManager
	};

});