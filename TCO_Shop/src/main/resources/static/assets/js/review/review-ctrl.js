clientApp.controller("review-ctrl", function($http, $scope) {
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