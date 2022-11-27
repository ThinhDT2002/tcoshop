const app3 = aungular.module("review-client-app", []);
app3.controller("review-client-ctrl", function($scope, $http){
	
	$scope.revs = [];
	$scope.form = [];
	
	
	$scope.initialize = function(){
		$http.get("/api/reviews").then(resp =>{
			$scope.revs = resp.data;
		})
		
	}
	
	$scope.initialize();
	
	$scope.create = function(){
		let rev = angular.copy($scope.form);
		$http.post("/api/revies", rev).then(resp => {
			$scope.revs.push(resp.data);
			alert("Thêm đánh giá sản phẩm thành công!");
			
		}).catch(error => {
			alert("Lỗi thêm mới đánh giá!");
			console.log("Error", error);
		});
		
	}
	
})