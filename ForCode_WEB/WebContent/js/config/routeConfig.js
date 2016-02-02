angular.module("forCode").config(function($routeProvider){

	$routeProvider.when("/home", {
		templateUrl: "view/home.html"
	});

	$routeProvider.when("/register", {
		templateUrl: "view/register.html",
		controller: "registerCtrl",
		resolve: {
			institutions: function(institutionService) {
				return institutionService.getInstitutions();
			}
		}
	});

	$routeProvider.when("/forgot", {
		templateUrl: "view/forgot.html"
	});

	$routeProvider.otherwise("/home", {
		templateUrl: "view/home.html"
	});
});