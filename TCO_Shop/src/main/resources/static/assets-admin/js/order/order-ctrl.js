adminApp.controller("order-ctrl", function($http, $scope) {
	$scope.orders = [];
	$scope.orderStatus = [];
	$scope.order = {};
	$scope.getOrders = function() {
		let url = "http://localhost:8080/api/orders";
		$http.get(url).then(resp => {
			$scope.orders = resp.data;
			$scope.orders.forEach(order => {
				if (order.user.fullname !== null && order.user.fullname !== undefined) {
					order.fullname = order.user.fullname;
				}
			})
		}).catch(error => {

		});
		let urlOrderStatus = "http://localhost:8080/api/orderStatus";
		$http.get(urlOrderStatus).then(resp => {
			$scope.orderStatus = resp.data;
		}).catch(error => {

		})
	}

	$scope.getOrders();

	$scope.orderProperty = '-id';

	$scope.sort = function() {

	}

	$scope.currentPage = 0;
	$scope.pageSize = "10";

	$scope.totalQuantity = function() {
		return $scope.orders.length;
	}


	$scope.numberOfPages = function() {
		return Math.ceil($scope.orders.length / $scope.pageSize);
	}

	$scope.pagination = function() {
		$scope.currentPage = 0;
	}

	$scope.updateOrderStatus = function(order) {
		$scope.order = angular.copy(order);
		let url = "http://localhost:8080/api/orders/" + $scope.order.id;
		$http.get(`/api/orders/id/${$scope.order.id}`).then(resp => {
			var trangThaiOld = "";
			if (resp.data.status == 'ChoXacNhan') {
				trangThaiOld = "Chờ xác nhận"
			} else if (resp.data.status == 'ChuanBi') {
				trangThaiOld = "Chuẩn bị hàng"
			} else if (resp.data.status == 'XuatKho') {
				trangThaiOld = "Xuất kho"
			} else if (resp.data.status == 'VanChuyen') {
				trangThaiOld = "Vận chuyển"
			} else if (resp.data.status == 'DaGiaoHang') {
				trangThaiOld = "Đã giao hàng"
			}
			var trangThaiNew = "";
			if ($scope.order.status == 'ChoXacNhan') {
				trangThaiNew = "Chờ xác nhận"
			} else if ($scope.order.status == 'ChuanBi') {
				trangThaiNew = "Chuẩn bị hàng"
			} else if ($scope.order.status == 'XuatKho') {
				trangThaiNew = "Xuất kho"
			} else if ($scope.order.status == 'VanChuyen') {
				trangThaiNew = "Vận chuyển"
			} else if ($scope.order.status == 'DaGiaoHang') {
				trangThaiNew = "Đã giao hàng"
			}
			let text = "Cập nhật trạng thái đơn hàng từ " + trangThaiOld + " sang " + trangThaiNew;
			if (confirm(text)) {
				if (resp.data.status == 'DaGiaoHang' && resp.data.isPaid == 2) {
					alert("Không thể thay đổi trạng thái đơn hàng đã được giao");
					location.reload();
				} else {
					$http.put(url, $scope.order).then(resp => {
						location.reload();
					}).catch(error => {
						console.log(error);
					})
				}
			} else {
				location.reload();
			}
		})
	}

	$scope.adminCancelOrder = function(order) {
		$scope.order = angular.copy(order);
		let text = "Sau khi huỷ đơn sẽ không thể thay đổi trạng thái! Bạn có muốn tiếp tục ?";
		$http.get(`/api/orders/id/${$scope.order.id}`).then(resp => {
			if (confirm(text)) {
				if (resp.data.status == 'DaGiaoHang' && resp.data.isPaid == 2) {
					alert("Không thể huỷ bỏ đơn hàng đã được giao");
					location.reload();
				} else {
					$http.put(`/api/orders/adminCancel/${$scope.order.id}`, $scope.order).then(resp => {
						location.reload();
					}).catch(error => {
						console.log(error);
					})
				}
			}
		})
	}

	$scope.orderDetails = [];
	$scope.findOrderDetails = function(orderId) {
		$http({
			url: "/api/ordersDetail/findByOrderId",
			method: "GET",
			params: {
				orderId: orderId
			}
		}).then(resp => {
			$scope.orderDetails = resp.data;
		}).then(function() {
			for (let i = 0; i < $scope.orderDetails.length - 1; i++) {
				if (JSON.stringify($scope.orderDetails[i].product) === JSON.stringify($scope.orderDetails[i + 1].product)) {
					$scope.orderDetails[i].product.price += $scope.orderDetails[i + 1].product.price;
					$scope.orderDetails[i].quantity += $scope.orderDetails[i + 1].quantity;
					$scope.orderDetails[i].price += $scope.orderDetails[i + 1].price;
					$scope.orderDetails.splice(i + 1, 1);
				}
			}
		})
	}

})

adminApp.filter('startFrom', function() {
	return function(input, start) {
		start = +start;
		return input.slice(start);
	}
})