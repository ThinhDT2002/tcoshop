clientApp.controller("product-ctrl", function($http, $scope) {
	let url = window.location.search;

	$scope.products = [];
	$scope.favorites = [];

	let arrUrl = url.split('=');
	let arrUrl2 = arrUrl[0].split('?');

	if (arrUrl2[1] == "cid") {
		$http({
			url: "/api/products/category/" + arrUrl[1],
			method: "GET",
		}).then(resp => {
			$scope.products = resp.data;
		}).then(resp => {
			let username = document.getElementById("currentUsername").innerText;
			if (username != "404") {
				$http({
					url: "/api/products/favorites",
					method: "GET",
					params: {
						username: username
					}
				}).then(resp => {
					$scope.favorites = resp.data;
					$scope.favorites.forEach(favorite => {
						let index = $scope.products.findIndex(product => favorite.product.id == product.id);
						if(index != -1) {
							$scope.products[index].isFavorite = true;
							$scope.products[index].favoriteId = favorite.id;
						}
					})
				})
			}
		})
	} else if (arrUrl2[1] == "scid") {
		$http({
			url: "/api/products/subcategory/" + arrUrl[1],
			method: "GET",
		}).then(resp => {
			$scope.products = resp.data;
		}).then(resp => {
			let username = document.getElementById("currentUsername").innerText;
			if (username != "404") {
				$http({
					url: "/api/products/favorites",
					method: "GET",
					params: {
						username: username
					}
				}).then(resp => {
					$scope.favorites = resp.data;
					$scope.favorites.forEach(favorite => {
						let index = $scope.products.findIndex(product => favorite.product.id == product.id);
						if(index != -1) {
							$scope.products[index].isFavorite = true;
							$scope.products[index].favoriteId = favorite.id;
						}
					})
				})
			}
		})
	} else {
		$http({
			url: "/api/products",
			method: "GET",
		}).then(resp => {
			$scope.products = resp.data;
		}).then(resp => {
			let username = document.getElementById("currentUsername").innerText;
			if (username != "404") {
				$http({
					url: "/api/products/favorites",
					method: "GET",
					params: {
						username: username
					}
				}).then(resp => {
					$scope.favorites = resp.data;
					$scope.favorites.forEach(favorite => {
						let index = $scope.products.findIndex(product => favorite.product.id == product.id);
						if(index != -1) {
							$scope.products[index].isFavorite = true;
							$scope.products[index].favoriteId = favorite.id;
						}
					})
				})
			}
		})
	}




	$scope.addFavorite = function(productId) {
		let username = document.getElementById("currentUsername").innerText;
		let favorite = {
			product: {
				id: productId
			},
			user: {
				username: username
			}
		}
		$http.post("/api/products/favorite/add", favorite).then(resp => {
			let index = $scope.products.findIndex(product => productId == product.id);
			$scope.products[index].isFavorite = true;
			$scope.products[index].favoriteId = resp.data.id;
			$scope.favorites.push(resp.data);
		})
	}
	$scope.removeFavorite = function(favoriteId) {
		$http.delete(`/api/products/favorite/remove/${favoriteId}`).then(resp => {
			let index = $scope.products.findIndex(product => product.favoriteId == favoriteId);
			$scope.products[index].isFavorite = undefined;
			$scope.products[index].favoriteId = undefined;
			let index2 = $scope.favorites.findIndex(favorite => favorite.id == favoriteId);
			$scope.favorites.slice(index2, 1);
		})
	}


	$scope.currentPage = 0;
	$scope.pageSize = "8";
	$scope.totalQuantity = function() {
		return $scope.products.length;
	}
	$scope.numberOfPages = function() {
		return Math.ceil($scope.products.length / $scope.pageSize);
	}
	$scope.pagination = function() {
		$scope.currentPage = 0;
	}
})