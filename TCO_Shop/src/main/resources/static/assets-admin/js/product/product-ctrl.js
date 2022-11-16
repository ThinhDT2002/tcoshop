adminApp.controller("product-ctrl", function($scope, $http){
	
	$scope.items = [];
	
	$scope.initialize = function(){
		$http.get("/api/products").then(resp => {
			$scope.items = resp.data;
		});
	}
	
	$scope.initialize();
	
	$scope.delete = function(item){
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
			headers: {'Content-type': undefined},
			enctype: 'multipart/form-data'
		})
	}
	

	adminApp.directive('convertToNumber', function(){
		return{
			require: 'ngModel',
			link: function(scope, element, atrs, ngModel){
				ngModel.$parsers.push(function(val){
					return parseInt(val, 10);
				});
				ngModel.$formatters.push(function(val){
					return '' + val;
				});
			}
		}
	});
	
	$scope.pager = {
		page: 0,
		size: "10",
		sortProperty: 'name',
		get items(){
			var start = this.page * Number(this.size);
			return $scope.items.slice(start, start + Number(this.size));
		},
		
		get count(){
		return Math.ceil(1.0 * $scope.items.length / Number(this.size));
		
		},
		get totalQuantity(){
			return $scope.items.length;
		},
		
		get repaginate(){
			this.page = 0;
			this.count;	
		},
		
		
		first(){
			this.page = 0;
		},
		prev(){
			this.page--;
			if(this.page < 0){
				this.last();
			}
			
		},
		next(){
			this.page++;		
			if(this.page >= this.count){
				this.first();
			}
			
		},
		last(){
			this.page = this.count -1;
		},
		// Không xóa cái hàm bên dưới
		 get sortBy() {

		},
	}
});