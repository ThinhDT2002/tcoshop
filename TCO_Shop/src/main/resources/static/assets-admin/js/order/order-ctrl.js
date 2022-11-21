adminApp.controller("order-ctrl", function($http, $scope) {
	$scope.orders = [];
	$scope.orderStatus = [];
	$scope.order = {};
	$scope.getOrders = function() {
		let url = "http://localhost:8080/api/orders";
		$http.get(url).then(resp => {
			$scope.orders = resp.data;
		}).catch(error => {
			console.log(error);
		});
		let urlOrderStatus = "http://localhost:8080/api/orderStatus";
		$http.get(urlOrderStatus).then(resp => {
			$scope.orderStatus = resp.data;
		}).catch(error => {
			console.log(error);
		})
	}
	
	$scope.getOrders();
	
	$scope.orderProperty = 'id';
	
	$scope.sort = function() {
		
	}
	
	$scope.currentPage = 0;
	$scope.pageSize = "10";
	
	$scope.numberOfPages = function() {
		return Math.ceil($scope.orders.length / $scope.pageSize);
	}
	// cái này t k biết để làm gì, kệ nó cứ ghi đi
	for(let i =0; i < 45; i++) {
		$scope.orders.push("Item " + i);
	}

	$scope.pagination = function() {
		$scope.currentPage = 0;
	}
	
	$scope.updateOrderStatus = function(order) {
		$scope.order = angular.copy(order);
		let url  = "http://localhost:8080/api/orders/" + $scope.order.id;
		$http.put(url, $scope.order).then(resp => {
			alert("Đã cập nhật trạng thái!");
		}).catch(error => {
			console.log(error);
		})
	}
	
})

adminApp.filter('startFrom', function() {
	return function(input, start) {
		start = +start;
		return input.slice(start);
	}
})