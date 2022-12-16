const clientApp = angular.module("client-app", ["ngRoute"]);
clientApp.config(function($routeProvider) {
	$routeProvider
		.when("/product/favorites", {
			templateUrl: " ",
			controller: "favorite-product-ctrl"
		})
		.when("/product/list", {
			templateUrl: " ",
			controller: "product-ctrl"
		})
		.when("/afterCheckout", {
			templateUrl: " ",
			controller: "after-checkout-ctrl"
		})
})
clientApp.filter('startFrom', function() {
	return function(input, start) {
		start = +start;
		return input.slice(start);
	}
})
