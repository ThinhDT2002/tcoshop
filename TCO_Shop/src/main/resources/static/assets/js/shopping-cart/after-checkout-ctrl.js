clientApp.controller("after-checkout-ctrl", function($scope, $http, $rootScope) {

	$scope.childMethod = function() {
		$rootScope.$emit("ClearCart", {});
	}
	$scope.childMethod();
})