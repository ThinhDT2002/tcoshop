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
	$scope.pageSize = "5";
	
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
	
	$scope.delete = function(user) {
		let text = "Bạn có muốn xoá người dùng này không ?";
		if(confirm(text) == true) {
			let currentUsername = document.getElementById("currentAuthentication").innerText;	
			if(currentUsername == user.username) {
				alert("Bạn không thể xoá chính mình");
			} else {
				$http({
					url: "/api/user",
					method: "DELETE",
					params: {
						username: user.username
					}
				}).then(resp => {
					alert("Xoá người dùng thành công");
					location.reload();
				}).catch(error => {
					alert("Có lỗi xảy ra khi xoá người dùng");
					console.log(error);
				})
			}
		} else {
			
		}	
	}

})

adminApp.filter('startFrom', function() {
	return function(input, start) {
		start = +start;
		return input.slice(start);
	}
})