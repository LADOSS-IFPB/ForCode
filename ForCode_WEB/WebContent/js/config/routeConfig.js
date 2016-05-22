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

angular.module("forCodeContestant").config(function($routeProvider){

	$routeProvider.when("/home", {
		templateUrl: "../view/templates/contestant/home.html"
	});
	
	$routeProvider.when("/contact", {
		templateUrl: "../view/templates/contestant/contact.html"
	});
	
	$routeProvider.when("/help", {
		templateUrl: "../view/templates/contestant/help.html"
	});
	
	$routeProvider.when("/profile", {
		templateUrl: "../view/templates/contestant/profile.html"
	});
	
	$routeProvider.when("/config", {
		templateUrl: "../view/templates/contestant/config.html"
	});
	
	$routeProvider.otherwise("/home", {
		templateUrl: "../view/templates/contestant/home.html"
	});
});