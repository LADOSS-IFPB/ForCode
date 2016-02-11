angular.module("forCodeConfig").factory("institutionService", function($http, config){
	
	var _getInstitutions = function(){
		return $http.get(config.baseUrl() + "/list/institutions")
	};

	return {
		getInstitutions: _getInstitutions
	};
})