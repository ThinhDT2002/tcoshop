adminApp.controller("category-ctrl", function($scope, $http) {
	$scope.imageChanged = function(files) {
		let data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/image/category', data, {
			transformRequest: angular.identity,
			headers: { 'Content-type': undefined },
			enctype: 'multipart/form-data'
		})
	}
	$scope.categories = [];
	$scope.subcategories = [];
	$http({
		url: "/api/categories",
		method: "GET"
	}).then(resp => {
		$scope.categories = resp.data;
	}).then(function() {
		$http({
			url: "/api/subcategory",
			method: "GET"
		}).then(resp => {
			$scope.subcategories = resp.data;
			$scope.categories.forEach(function(category, index) {
				$scope.categories[index].subcategories = [];
				$scope.subcategories.forEach(function(subcategory) {
					if(subcategory.category.id == category.id) {
						$scope.categories[index].subcategories.push(subcategory);
					}
				})				
			})
			console.log($scope.categories);
		})
	})
	
	$scope.cateProperty = 'id';
	$scope.sort = function(){
		
	}
	$scope.currentPage = 0;
	$scope.pageSize = "5";
	$scope.totalQuantity = function(){
		return $scope.categories.length;
	}
	$scope.numberOfPages = function(){
		return Math.ceil($scope.categories.length / $scope.pageSize);
	}
	for(let i= 0; i< 45; i++){
		$scope.categories.push("Item " + i);
	}
	
	$scope.pagination = function(){
		$scope.currentPage = 0;
	}
	
})
adminApp.filter('startFrom', function() {
	return function(input, start) {
		start = +start;
		return input.slice(start);
	}
});