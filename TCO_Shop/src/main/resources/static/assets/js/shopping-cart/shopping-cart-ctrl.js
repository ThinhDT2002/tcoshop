clientApp.controller("shopping-cart-ctrl", function($scope, $http, $rootScope) {
	$scope.products = [];
	$scope.reviews = [];

	$scope.newProducts = [];
	$scope.newProducts1 = [];
	$scope.newProducts2 = [];
	$http.get("/api/products/newProducts").then(resp => {
		$scope.newProducts = resp.data;
	}).then(function() {
		for (let i = 0; i < $scope.newProducts.length; i++) {
			if (i <= 3) {
				$scope.newProducts1.push($scope.newProducts[i]);

			} else {
				$scope.newProducts2.push($scope.newProducts[i]);
			}
		}
	})

	$scope.highDiscountProducts = [];
	$scope.highDiscountProducts1 = [];
	$scope.highDiscountProducts2 = [];
	$http.get("/api/products/highDiscountProducts").then(resp => {
		$scope.highDiscountProducts = resp.data;
	}).then(function() {
		$scope.discountProduct1 = $scope.highDiscountProducts[1];
		$scope.discountProduct2 = $scope.highDiscountProducts[2];
		for (let i = 0; i < $scope.highDiscountProducts.length; i++) {
			if (i <= 3) {
				$scope.highDiscountProducts1.push($scope.highDiscountProducts[i]);
			} else {
				$scope.highDiscountProducts2.push($scope.highDiscountProducts[i]);
			}
		}
	})

	$scope.bestSoldProducts = [];
	$scope.bestSoldProducts1 = [];
	$scope.bestSoldProducts2 = [];
	$http.get("/api/products/bestSoldProducts").then(resp => {
		$scope.bestSoldProducts = resp.data;
	}).then(function() {
		for (let i = 0; i < $scope.bestSoldProducts.length; i++) {
			if (i <= 3) {
				$scope.bestSoldProducts1.push($scope.bestSoldProducts[i]);
			} else {
				$scope.bestSoldProducts2.push($scope.bestSoldProducts[i]);
			}
		}
	})

	$scope.cheapProducts = [];
	$http.get("/api/products/cheapProducts").then(resp => {
		$scope.cheapProducts = resp.data;
	}).then(function() {
		$scope.cheapProduct1 = $scope.cheapProducts[1];
		$scope.cheapProduct2 = $scope.cheapProducts[2];
		$scope.cheapProduct3 = $scope.cheapProducts[3];
		$scope.cheapProduct4 = $scope.cheapProducts[4];
	})

	// lấy giỏ hàng của người dùng abcxyz
	$scope.items = [];
	$scope.transaction = [];
	// lấy tên của người dùng
	var name = $("#nguoidunghientai").text();
	var split = name.split(" ");

	$scope.initialize = function() {
		$http.get("/api/reviews/top10").then(resp => {
			$scope.reviews = resp.data;
		});
		$http.get("/api/products").then(resp => {
			$scope.products = resp.data;
		}).then(function() {
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
						$scope.products[index].isFavorite = true;
						$scope.products[index].favoriteId = favorite.id;
					})
				})
			}
		});
		$http.get(`/api/orders/${split[2]}`).then(resp => {
			$scope.items = resp.data;
		});
		$http.get(`/api/transaction/${split[2]}`).then(resp => {
			$scope.transaction = resp.data;
		});
	}
	$scope.itemProperty = "-id";
	$scope.cancelOrder = function(orderId) {
		$http.put(`/api/orders/cancel/${orderId}`).then(resp => {
			location.reload();
		}).catch(error => {
			console.log(error)
		})
	}

	// con mắt ở trang index home
	$scope.display = {
		items: null,
		get(id) {
			$http.get(`/api/products/${id}`).then(resp => {
				this.items = resp.data;
			})
		}
	}

	$scope.favorites = [];
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
	$scope.initialize();

	$scope.pager = {
		page: 0,
		size: 8,
		get items() {
			var start = this.page * this.size;
			return $scope.products.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	}

	// thay doi hinh anh nguoi dung
	$scope.imageChanged = function(files) {
		let data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/image/user', data, {
			transformRequest: angular.identity,
			headers: { 'Content-type': undefined },
			enctype: 'multipart/form-data'
		})
	}
	$rootScope.$on("ClearCart", function() {
		$scope.cart.clear();
	})
	$scope.cart = {
		items: [],
		add(id) {
			var item = this.items.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToSessionStorage();
			} else {
				$http.get(`/api/products/${id}`).then(resp => {
					resp.data.qty = 1;
					this.items.push(resp.data);
					this.saveToSessionStorage();
				})
			}
		},

		remove(id) {
			var index = this.items.findIndex(item => item.id == id);
			this.items.splice(index, 1);
			this.saveToSessionStorage();
		},

		clear() {
			this.items = [];
			this.saveToSessionStorage();
		},

		get count() {
			return this.items
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},

		get amount() {
			return this.items
				.map(item => item.qty * item.price * (100 - item.discount) / 100)
				.reduce((total, qty) => total += qty, 0);
		},

		saveToSessionStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			sessionStorage.setItem("cart-tco", json);
		},

		loadFromSessionStorage() {
			var json = sessionStorage.getItem("cart-tco");
			this.items = json ? JSON.parse(json) : [];
		},

		get ship() {
			if (this.count == 0) return 0;
			else if (this.count <= 3) return 12000;
			else if (this.count >= 4) return 0;
		},

	}

	$scope.cart.loadFromSessionStorage();

	$scope.order = {
		createDate: new Date(),
		address: "",
		user: { username: split[2] },
		description: "",
		phoneNumber: "",
		status: "ChoXacNhan",
		isPaid: 1,
		get expectedDate() {
			let date = new Date();
			let numberOfDayToAdds = 7;
			return date.setDate(date.getDate() + numberOfDayToAdds);
		},
		get orderTimeDetail() {
			let date = new Date();
			let currentHour = date.toLocaleTimeString();
			return currentHour;
		},
		shippingCost: $scope.cart.ship,
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					product: { id: item.id },
					price: item.price * ((100 - item.discount) / 100),
					quantity: item.qty
				}
			});
		},


		purchase() {
			if ($scope.order.phoneNumber.match("^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$")) {
				if ($scope.cart.count > 0) {
					var order = angular.copy(this);
					$http.post("/api/orders", order).then(resp => {
						alert("Đặt hàng thành công");
						$scope.cart.clear();
						returnOrder = resp.data;
						location.href = "/order/track/" + resp.data.id;
					}).catch(error => {
						console.log(error)
						alert("Có lỗi xảy ra trong quá trình đặt hàng! Vui lòng thử lại")
					})
				} else {
					alert("Bạn chưa có sản phẩm trong giỏ hàng")
				}
			} else {
				document.getElementById("sdt").style.display = "flex";
			}
		},

	};
})
