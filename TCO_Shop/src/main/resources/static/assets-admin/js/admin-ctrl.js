// USER CTRL
const app = angular.module("user-list-app",[])
app.controller("user-list-ctrl", function($scope, $http) {
    $scope.users = [];
 
    $http.get("/api/user").then(resp=> {
        $scope.users = resp.data;
    }) 
})
// USER MANAGEMENT CTRL
const userManagementApp = angular.module("user-management-app",[]);
userManagementApp.controller("user-management-ctrl", function($scope, $http) {
	
	$scope.imageChanged = function(files) {
		let data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/image/user', data, {
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			enctype: 'multipart/form-data'
		})
	}
})
// PRODUCT CTRL
const app2 = angular.module("product-app",[]);
app2.controller("product-ctrl", function($scope, $http){
	
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
	

	app.directive('convertToNumber', function(){
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
// CATEGORY CTRL
const app3 = angular.module("category-app", []);
app3.controller("category-ctrl", function($scope, $http){
	$scope.imageChanged = function(files) {
		let data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/image/category', data, {
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			enctype: 'multipart/form-data'
		})
	}
	
	
});

// SUBCATEGORY
const app4 = angular.module("subcategory-app", []);
app4.controller("subcategory-ctrl", function($scope, $http){
	$scope.subs = [];
	$scope.form = {};
	$scope.cates = [];
	
	$scope.initialize = function(){
		$http.get("/api/subcategory").then(resp => {
			$scope.subs = resp.data;
			$scope.form = {
			icon: 'default-subcategory.png'
			};
		});
		
		$http.get("/api/categories").then(resp =>{
			$scope.cates = resp.data;
		});
		
	}
	
	$scope.initialize();
	
	$scope.imageChanged = function(files) {
		let data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/image/save/subcategory', data, {
			transformRequest: angular.identity,
			headers: {'Content-type': undefined},
			enctype: 'multipart/form-data'
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error =>{
			alert("Lỗi upload hình ảnh!");
			console.log("Error", error);
		}),
		$http.post('/api/image/subcategory', data, {
			transformRequest: angular.identity,
			headers:{'Content-type': undefined},
			enctype: 'multipart/form-data'
		})
	}
	
	$scope.edit = function(sub){
		$scope.form = angular.copy(sub);
		document.getElementById('text').readOnly = true;
	}
	
	$scope.create = function(){
		let sub  = angular.copy($scope.form);
		let imageUpload = document.getElementById('imageUpload').value;
		imageUpload = imageUpload.replace(/.*[\/\\]/, '');
		if(imageUpload == ''){
			imageUpload = 'default-subcategory.png';
		}
		sub['icon'] = imageUpload;
		let subId = sub['id'];
		let removeSpaceSubId = subId.replaceAll(" ", "");
		let removeSpecialCharacterSubId = removeSpaceSubId.replace(/[^\w\s]/gi, '');
		sub['id'] = removeSpecialCharacterSubId;
		var exist = false;
		$scope.subs.forEach(function (subcategory) {
			let id = subcategory['id'];
			if(id == sub['id']) {
				exist = true;
			}
		});
		if(exist == true) {
			alert("Mã thương hiệu đã tồn tại!");
		} else {
			$http.post("/api/subcategory", sub).then(resp => {
				$scope.subs.push(resp.data);
				alert("Thêm thương hiệu thành công!");
				$scope.reset();
			}).catch(error =>{
				alert("Lỗi thêm mới thương hiệu!");
				console.log("Error", error);
				
			});
		}
				
	}
	
	$scope.update = function(){
		let sub = angular.copy($scope.form);
		let imageUpload = document.getElementById('imageUpload').value;
		imageUpload = imageUpload.replace(/.*[\/\\]/, '');
		if(imageUpload == ''){
			imageUpload = 'default-subcategory.png';
		}
		sub['icon'] = imageUpload;
		$http.put(`/api/subcategory/${sub.id}`, sub).then(resp =>{
			let index = $scope.subs.findIndex(s => s.id == sub.id);
			$scope.subs[index] = sub;
			alert("Cập nhật thương hiệu thành công!");
			$scope.reset();
		}).catch(error =>{
			alert("Lỗi cập nhật thương hiệu");
			console.log("Error", error);
		});
	}
	
	$scope.delete = function(sub){
		$http.delete(`/api/subcategory/${sub.id}`).then(resp => {
			let index = $scope.subs.findIndex(s => s.id == sub.id);
			$scope.subs.splice(index, 1);
			alert("Xóa thương hiệu thành công!");
		}).catch(error =>{
			alert("Lỗi xóa thương hiệu!");
			console.log("Errror", error);
		});
	}
	
	$scope.reset = function(){
		$scope.form = {
			icon: 'default-subcategory.png'
		};
		document.getElementById('text').readOnly = false;
	}
	
	$scope.pager = {
			page: 0,
			size: '10',
			sortProperty: 'name',
			get subs(){
				var start = this.page * Number(this.size);
				return $scope.subs.slice(start, start + Number(this.size));
			},
			get count(){
			return Math.ceil(1.0 * $scope.subs.length / Number(this.size));
			},
			get totalQuantity(){
			return $scope.subs.length;
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
				this.page = this.count - 1;
			},
			get sortBy(){
				
			}
			
		}
	
});

	