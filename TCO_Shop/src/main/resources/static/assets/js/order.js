const app2 = angular.module("user-history", [])
app2.controller("user-history", function($scope, $http) {
	$scope.items = [];
	
	$scope.user = $("#username").text();
	$scope.initialize = function(){

		
		$http.get(`/api/orders/${$scope.user}`).then(resp => {
			$scope.items = resp.data;
		});
		

	}

	$scope.initialize();


})