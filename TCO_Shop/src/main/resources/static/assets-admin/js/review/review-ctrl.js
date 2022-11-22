adminApp.controller("review-ctrl", function($scope, $http){
	
	$scope.revs = [];
	$scope.form = [];
	
	
	$scope.initialize = function(){
		$http.get("/api/reviews").then(resp =>{
			$scope.revs = resp.data;
		})
		
	}
	
	$scope.initialize();
})