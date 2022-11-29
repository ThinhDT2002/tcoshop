const app = angular.module("shopping-cart-app", []);

app.controller("shopping-cart-ctrl", function($scope, $http) {
	$scope.products = [];

	// lấy giỏ hàng của người dùng abcxyz
	$scope.items = [];
	// lấy tên của người dùng
	var name = $("#username").text();
	var split = name.split(" ");
	console.log(split[2]);

	$scope.initialize = function() {
		$http.get("/api/products").then(resp => {
			$scope.products = resp.data;
		});

		$http.get(`/api/orders/${split[2]}`).then(resp => {
			$scope.items = resp.data;
		});
	}
	
	// con mắt ở trang index home
	$scope.display = {
		items : null,
		get(id) {			
			$http.get(`/api/products/${id}`).then(resp => {
				this.items = resp.data;
			})
		}
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
				.map(item => item.qty * item.price)
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

		// format giá tiền
		formatNumber(value) {
			return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		},

		// tính phí ship
		vat() {
			return 30000;
		},

		get ship() {
			if (this.count == 0) {
				return 0;
			} else if (this.count > 2) {
				return 15000;
			} else if (this.amount > 10000000) {
				return 0;
			}

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
			if ($scope.cart.count > 0) {
				var order = angular.copy(this);
				$http.post("/api/orders", order).then(resp => {
					alert("Đặt hàng thành công");
					$scope.cart.clear();
					location.href = "/order/track/" + resp.data.id;
				}).catch(error => {
					console.log(error)
					alert("Đặt hàng lỗi!")
				})
			} else {
				alert("Bạn chưa có sản phẩm trong giỏ hàng")
			}
		},

	};



})