adminApp.controller("transaction-ctrl", function($http, $scope){
	
	$scope.transaction = [];
	$scope.getTransaction = function(){
		$http.get("/api/transaction").then(resp => {
			$scope.transaction = resp.data;
		})
	}
	
	$scope.getTransaction();
	
	$scope.transProperty = 'id';
	$scope.sort = function(){
		
	}
	$scope.currentPage = 0;
	$scope.pageSize = '3';
	
	$scope.totalQuantity = function(){
		return $scope.transaction.length;
	}
	
	$scope.numberOfPages = function(){
		return Math.ceil($scope.transaction.length / $scope.pageSize);
	}
	
	for(let i= 0; i< 45; i++){
		$scope.transaction.push("Item " + i);
	}
	
	$scope.pagination = function(){
		$scope.currentPage = 0;
	}
	
})
adminApp.filter('startFrom', function(){
	return function(input, start){
		start = +start;
		return input.slice(start);
	}
})