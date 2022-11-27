adminApp.controller("user-list-ctrl", function($scope, $http) {
    $scope.users = [];
 
	 $scope.getUsers = function(){
		$http.get("/api/user").then(resp=> {
	        $scope.users = resp.data;
	    })
	}
    $scope.getUsers();
    
    $scope.userProperty = 'username';
    
    $scope.sort = function(){
	
	}
	$scope.currentPage = 0;
	$scope.pageSize = "2";
	
	$scope.totalQuantity = function(){
		return $scope.users.length;
	}
	$scope.numberOfPages = function(){
		return Math.ceil($scope.users.length / $scope.pageSize);
	}
	for(let i =0; i < 45; i++){
		$scope.users.push("Item " + i);
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
})