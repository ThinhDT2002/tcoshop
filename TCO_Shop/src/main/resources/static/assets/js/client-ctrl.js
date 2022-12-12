const clientApp = angular.module("client-app", ["ngRoute"]);
clientApp.config(function($routeProvider) {
	$routeProvider
	.when("/product/favorites", {
		templateUrl: " ",
		controller: "favorite-product-ctrl"
	})
})
clientApp.filter('startFrom', function() {
	return function(input, start) {
		start = +start;
		return input.slice(start);
	}
})
