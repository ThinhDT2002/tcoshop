adminApp.controller("order-ctrl", function($http, $scope) {
	$scope.orders = [];
	
	$scope.getOrders = function() {
		let url = "http://localhost:8080/api/orders";
		$http.get(url).then(resp => {
			$scope.orders = resp.data;
			console.log($scope.orders);
		}).catch(error => {
			console.log(error);
		})
	}
	
	$scope.getOrders();
	
})