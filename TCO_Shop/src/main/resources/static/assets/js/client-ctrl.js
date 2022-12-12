const app = angular.module("shopping-cart-app", []);

app.controller("shopping-cart-ctrl", function($scope, $http) {
	$scope.products = [];
	$scope.reviews = [];
	
	$scope.newProducts = [];
	$scope.newProducts1 = [];
	$scope.newProducts2 = [];
	$http.get("/api/products/newProducts").then(resp => {
		$scope.newProducts = resp.data;
	}).then(function() {
		for(let i = 0; i < $scope.newProducts.length; i++) {
			if(i <= 3) {
				$scope.newProducts1.push($scope.newProducts[i]);

			}  else {
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
		for(let i = 0; i < $scope.highDiscountProducts.length; i++) {
			if(i <= 3) {
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
		for(let i = 0; i < $scope.bestSoldProducts.length; i++) {
			if(i <= 3) {
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
	// lấy tên của người dùng
	var name = $("#nguoidunghientai").text();
	var split = name.split(" ");

	$scope.initialize = function() {
		$http.get("/api/reviews/top10").then(resp => {
			$scope.reviews = resp.data;
		});
		$http.get("/api/products").then(resp => {
			$scope.products = resp.data;
		}).then(function(){
			let username = document.getElementById("currentUsername").innerText;
			if(username != "404") {
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
				})
				console.log($scope.products);
			})
		}
		});			
		$http.get(`/api/orders/${split[2]}`).then(resp => {
			$scope.items = resp.data;
		});
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
		$http.post("/api/products/favorite/add", favorite).then(resp=> {
			$scope.favorites.push(resp.data);
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
				.map(item => item.qty * item.price * (100 - item.discount)/100)
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
			else if(this.count >= 1) return 12000;
			else if (this.count > 5) return 0;
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
			if($scope.order.phoneNumber.match("^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$")){
				

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
			}else{
				document.getElementById("sdt").style.display = "flex";
				
			}
		},

	};
})

app.controller("review-ctrl", function($http, $scope) {
	let currentUrl = window.location.pathname;
	const urlArray = currentUrl.split("/");
	let productId = urlArray[urlArray.length - 1];
	$scope.reviews = [];
	$scope.currentAuthenticationUsername = "";
	let currentUsername = document.getElementById("nguoidunghientai").innerText;
	if (currentUsername == "Tài khoản") {

	} else {
		const arr = currentUsername.split(" ");
		currentUsername = arr[arr.length - 1];
		$scope.currentAuthenticationUsername = currentUsername;
	}
	$http({
		url: "/api/reviews/" + productId,
		method: "GET"
	}).then(resp => {
		$scope.reviews = resp.data;
	})
	$scope.reviewForm = {};
	$scope.createReview = function() {
		let currentDate = new Date();
		let currentHour = currentDate.toLocaleTimeString();
		let reviewData = angular.copy($scope.reviewForm);
		if (reviewData.content === undefined) {

		} else {
			let currentUser = document.getElementById("nguoidunghientai").innerText;
			let currentUserAvatar = "";
			if (currentUser == "Tài khoản") {
				currentUser = "Guest";
				currentUserAvatar = "avatar1.png";
				reviewData.time = new Date();
				reviewData.user = {
					username: currentUser,
					avatar: currentUserAvatar
				};
				reviewData.timeDetail = currentHour;
				reviewData.edited = 0;
				reviewData.product = {
					id: productId
				}

				$http.post("/api/reviews", reviewData).then(resp => {
					$scope.reviews.push(resp.data);
					$scope.reviewForm = {};
				})

			} else {
				const arr = currentUser.split(" ");
				currentUser = arr[arr.length - 1];
				$http({
					url: "/api/user/" + currentUser,
					method: "GET"
				}).then(resp => {
					currentUserAvatar = resp.data.avatar;
				}).then(function() {
					reviewData.time = new Date();
					reviewData.user = {
						username: currentUser,
						avatar: currentUserAvatar
					};
					reviewData.timeDetail = currentHour;
					reviewData.edited = 0;
					reviewData.product = {
						id: productId
					}

					$http.post("/api/reviews", reviewData).then(resp => {
						$scope.reviews.push(resp.data);
						$scope.reviewForm = {};
					})

				})
			}			
		}
	}
	$scope.editReviewForm = {};
	var currentReview = "";
	$scope.edit = function(review) {
		$scope.editReviewForm = review;
		currentReview = $scope.editReviewForm.content;
		document.getElementById("editReview" + review.id).style.display = "block";
		document.getElementById("contentReview" + review.id).style.display = "none";
	}

	$scope.undo = function(review) {
		$scope.editReviewForm.content = currentReview;
		document.getElementById("editReview" + review.id).style.display = "none";
		document.getElementById("contentReview" + review.id).style.display = "block";
	}

	$scope.updateReview = function(editedReview) {
		let currentDate = new Date();
		let currentHour = currentDate.toLocaleTimeString();
		let currentUser = document.getElementById("nguoidunghientai").innerText;
		let currentUserAvatar = "";
		const arr = currentUser.split(" ");
		currentUser = arr[arr.length - 1];
		$http({
			url: "/api/user/" + currentUser,
			method: "GET"
		}).then(resp => {
			currentUserAvatar = resp.data.avatar;
		}).then(function() {
			editedReview.time = new Date();
			editedReview.user = {
				username: currentUser,
				avatar: currentUserAvatar
			};
			editedReview.product = {
				id: productId
			}
			editedReview.timeDetail = currentHour;
			editedReview.edited = 1;
			$http.put("/api/reviews", editedReview).then(resp => {
				let index = $scope.reviews.findIndex(rv => rv.id == editedReview.id);
				$scope.reviews[index] = editedReview;
				document.getElementById("editReview" + editedReview.id).style.display = "none";
				document.getElementById("contentReview" + editedReview.id).style.display = "block";
			})

		})
	}

	$scope.delete = function(reviewId) {
		let text = "Xoá đánh giá này ?";
		if (confirm(text) == true) {
			$http({
				url: "/api/reviews",
				method: "DELETE",
				params: {
					reviewId: reviewId
				}
			}).then(resp => {
				let index = $scope.reviews.findIndex(rv => rv.id == reviewId);
				$scope.reviews.splice(index, 1);
				alert("Xoá thành công");
			})
		}
	}
})