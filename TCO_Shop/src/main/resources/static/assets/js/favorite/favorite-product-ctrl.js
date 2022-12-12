clientApp.controller("favorite-product-ctrl", function($http, $scope) {
	let currentUser = document.getElementById("currentUsername").innerText;
	$scope.favoriteProducts = [];
	$http({
		url: "/api/products/favorites",
		method: "GET",
		params: {
			username: currentUser
		}
	}).then(resp => {
		$scope.favoriteProducts = resp.data;
	})
	
	$scope.remove = function(favoriteId) {
		$http.delete(`/api/products/favorite/remove/${favoriteId}`).then(resp => {
			let index = $scope.favoriteProducts.findIndex(favorite => favorite.id == favoriteId);
			$scope.favoriteProducts.splice(index, 1);
		})
	}
	
	$scope.currentPage = 0;
	$scope.pageSize = "8";
	$scope.totalQuantity = function(){
		return $scope.favoriteProducts.length;
	}
	$scope.numberOfPages = function() {
		return Math.ceil($scope.favoriteProducts.length / $scope.pageSize);
	}
	$scope.pagination = function() {
		$scope.currentPage = 0;
	}
})