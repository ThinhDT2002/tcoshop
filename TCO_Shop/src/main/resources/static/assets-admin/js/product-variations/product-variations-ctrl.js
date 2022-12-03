adminApp.controller("product-variations-ctrl", function($http, $scope) {
	$scope.productVariationForm = {};
	$scope.productVariations = [];
	$http({
		url: "/api/variation/all",
		method: "GET"
	}).then(resp => {
		$scope.productVariations = resp.data;
	})
	$scope.create = function(productVariation) {
		let index = $scope.productVariations.findIndex(pV => pV.id == productVariation.id);
		if (index != -1) {
			alert("ID đã tồn tại");
		} else {
			$http.post("/api/variation", productVariation).then(resp => {
				$scope.productVariations.push(resp.data);
				$scope.productVariationForm = {};
				alert("Thêm mới thành công");
			})
		}

	}
	$scope.productVariationsProperty = 'id';
	
	$scope.sort = function() {
		
	}
	
	$scope.pagination = function() {
		$scope.currentPage = 0;
	}
	
	$scope.numberOfPages = function() {
		return Math.ceil($scope.productVariations.length / $scope.pageSize);
	}
	
	$scope.pageSize = "8";
	$scope.currentPage = 0;
	
	$scope.edit = function(productVariation) {
		$scope.productVariationForm = productVariation;
		document.getElementById("variationID").readOnly = true;
		document.getElementById("updateButton").style.display = "inline-block";
		document.getElementById("createButton").style.display = "none";
	}
	
	$scope.reset = function() {
		$scope.productVariationForm = {};
		document.getElementById("variationID").readOnly = false;
		document.getElementById("updateButton").style.display = "none";
		document.getElementById("createButton").style.display = "inline-block";
	}
	
	$scope.update = function(productVariation) {
		$http.put("/api/variation", productVariation).then(resp => {
			let index = $scope.productVariations.findIndex(pV => pV.id == productVariation.id);
			$scope.productVariations[index] = resp.data;
			$scope.reset();
			alert("Cập nhật thành công");
		})
	}
	
	$scope.delete = function(productVariationId) {
		let text = "Bạn muốn xoá mục này ?";
		if(confirm(text) == true) {
			$http({
				url: "/api/variation",
				method: "DELETE",
				params: {
					id: productVariationId
				}
			}).then(resp => {
				let index = $scope.productVariations.findIndex(pV => pV.id == productVariationId);
				$scope.productVariations.splice(index, 1);
				alert("Xoá thành công");
			})
		}
	}
})