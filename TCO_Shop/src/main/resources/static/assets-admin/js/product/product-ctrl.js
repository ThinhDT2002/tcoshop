adminApp.controller("product-ctrl", function($scope, $http) {

	$scope.items = [];
	$scope.allProducts = [];
	$scope.initialize = function() {
		$http.get("/api/products").then(resp => {
			$scope.items = resp.data;
		}).then(function() {
			$scope.allProducts = [...$scope.items];
		});
	}

	$scope.initialize();

	$scope.delete = function(item) {
		$http.delete(`/api/products/${item.id}`).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items.splice(index, 1);
			$scope.reset();
			alert("Xóa sản phẩm thành công");
		}).catch(error => {
			alert("Lỗi xóa sản phẩm");
			console.log("Error", error);
		});
	}

	$scope.imageChanged = function(files) {
		let data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/image/products', data, {
			transformRequest: angular.identity,
			headers: { 'Content-type': undefined },
			enctype: 'multipart/form-data'
		})
	}


	adminApp.directive('convertToNumber', function() {
		return {
			require: 'ngModel',
			link: function(scope, element, atrs, ngModel) {
				ngModel.$parsers.push(function(val) {
					return parseInt(val, 10);
				});
				ngModel.$formatters.push(function(val) {
					return '' + val;
				});
			}
		}
	});

	$scope.sortProperty = 'name';

	$scope.sort = function() {

	}

	$scope.currentPage = 0;
	$scope.pageSize = "10";

	$scope.totalQuantity = function() {
		return $scope.items.length;
	}

	$scope.numberOfPages = function() {
		return Math.ceil($scope.items.length / $scope.pageSize);
	}
	for (let i = 0; i < 45; i++) {
		$scope.items.push("Item " + i);
	}

	$scope.pagination = function() {
		$scope.currentPage = 0;
	}

	$scope.productVariations = [];
	$scope.productVariationValues = [];
	$http.get("/api/variation/all").then(resp => {
		$scope.productVariations = resp.data;
	}).then(function() {
		$scope.productVariations = $scope.productVariations;
		$scope.productVariationValues = new Array($scope.productVariations.length);
	})
	
	$scope.categoryFilter = "all";
	$scope.changeCategory = function() {
		$scope.items = [];
		if($scope.categoryFilter == 'all') {
			$scope.items = [...$scope.allProducts];
		} else {
			$scope.allProducts.forEach(product => {
				if(product.category.id == $scope.categoryFilter) {
					$scope.items.push(product);
				}
			})
		}
	}
})
adminApp.filter('startFrom', function() {
	return function(input, start) {
		start = +start;
		return input.slice(start);
	}
});