const app = angular.module("user-list-app", [])
app.controller("user-list-ctrl", function($scope, $http) {
	$scope.items = [];

	$http.get("/api/orders").then(resp => {
		$scope.items = resp.data;
	});

	$scope.initialize = function(item) {
		$http.delete(`/api/products/${item.user}`).then(resp => {
			var index = $scope.items.findIndex(p => p.user == item.user);
			alert("Xóa sản phẩm thành công");
		}).catch(error => {
			alert("Lỗi xóa sản phẩm");
			console.log("Error", error);
		});
	}

	$scope.initialize();


})