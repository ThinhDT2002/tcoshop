adminApp.controller("review-ctrl", function($scope, $http){
	
	$scope.revs = [];
	
	$scope.initialize = function(){
		$http.get("/api/reviews").then(resp =>{
			$scope.revs = resp.data;
		})
		
	}
	/*
	$scope.delete = function(rev){
		$http.delete(`/api/reviews/${rev.id}`).then(resp => {
			var index = $scope.revs.findIndex(r => r.id == rev.id);
			$scope.revs.splice(index, 1);
			alert("Xóa đánh giá thành công!");
		}).catch(error => {
			alert("Lỗi xóa đánh giá!");
			console.log("Error", error);
		});
	}
	*/
	
	
	$scope.initialize();
	
	$scope.delete = function(reviewId){
		let text = "Xóa đánh giá này ?";
		if(confirm(text) == true){
			$http({
				url: "/api/reviews",
				method: "DELETE",
				params: {
					reviewId: reviewId
				}
			}).then(resp => {
				let index = $scope.revs.findIndex(r => r.id == reviewId);
				$scope.revs.splice(index, 1);
				alert("Xóa đánh giá thành công!");
			}).catch(error => {
				alert("Lỗi xóa đánh giá!");
				console.log("Error", error);
			});
		}
	}
	
	$scope.reviewProperty = 'id';
	$scope.sort = function(){
		
	}
	$scope.currentPage = 0;
	$scope.pageSize = '5';
	
	$scope.totalQuantity = function(){
		return $scope.revs.length;
	}
	
	$scope.numberOfPages = function(){
		return Math.ceil($scope.revs.length / $scope.pageSize);
	}
	
	for(let i= 0; i< 45; i++){
		$scope.revs.push("Item " + i);
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