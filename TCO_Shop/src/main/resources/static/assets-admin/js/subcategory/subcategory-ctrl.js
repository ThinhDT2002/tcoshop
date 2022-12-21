adminApp.controller("subcategory-ctrl", function($scope, $http){
	$scope.subs = [];
	$scope.form = {};
	$scope.cates = [];
	
	$scope.initialize = function(){   				
		$http.get("/api/subcategory").then(resp => { //Sau khi mở trang sẽ chạy code http
			$scope.subs = resp.data;
			$scope.form = {
			icon: 'default-subcategory.png' // Hình mặt định png bên trang điền form
			};
			
		});
		
		$http.get("/api/categories").then(resp =>{ //Sau khi trả dữ liệu về resp
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
			$scope.form.image = resp.data.name; //Hàm đổi hình
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
		document.getElementById('textId').readOnly = true; // Id không thể chỉnh sửa
		document.getElementById('createButton').style.display="none"; //Nút thêm sẽ ẩn đi trong khi chỉnh sửa hoặc hiện khi mới lại trang
		document.getElementById('updateButton').style.display="inline-block"; //Nút cập nhật sẽ hiện hoặc sẽ mất khi chỉnh sửa
	}
	
	$scope.create = function(){
		let sub  = angular.copy($scope.form);
		let imageUpload = document.getElementById('imageUpload').value;
		imageUpload = imageUpload.replace(/.*[\/\\]/, ''); //Thay thế các kí tự thừa trong ảnh
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
			alert("Thương hiệu này có chứa sản phẩm không thể xóa !");
			console.log("Errror", error);
		});
	}
	
	$scope.reset = function(){
		$scope.form = {
			icon: 'default-subcategory.png'
		};
		document.getElementById('textId').readOnly = false;
		document.getElementById('createButton').style.display="inline-block";
		document.getElementById('updateButton').style.display="none";
	}
	
	$scope.subProperty = 'id';
	
	$scope.sort = function(){
		
	}
	
	$scope.currentPage = 0;
	$scope.pageSize = "8";
	
	$scope.totalQuantity = function(){
		return $scope.subs.length;
	}
	
	$scope.numberOfPages = function(){
		return Math.ceil($scope.subs.length / $scope.pageSize);
	}
	
	for(let i= 0; i< 45; i++){
		$scope.subs.push("Item " + i);
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