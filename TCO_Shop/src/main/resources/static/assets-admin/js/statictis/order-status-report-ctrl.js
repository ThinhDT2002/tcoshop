adminApp.controller("order-status-report-ctrl", function($http, $scope) {
	$scope.allOrderYear = [];
	$http.get("/api/report/saleReport/allYearOrder").then(resp => {
		$scope.allOrderYear = resp.data;
	})
	$scope.orders = [];
	$scope.ordersDetail = [];
	$scope.statusOrder;
	$scope.yearOrderStatus;
	$scope.getOrderStatusDetailReport = function(year, status) {
		$scope.statusOrder = status;
		$scope.yearOrderStatus = year;
		$http({
			url: "/api/report/orderStatusReportDetail",
			method: "GET",
			params: {
				year: year,
				status: status
			}
		}).then(resp => {
			
			$scope.orders = resp.data;
			console.log($scope.orders);
			
		})

		$http.get("/api/ordersDetail").then(resp => {
			$scope.ordersDetail = resp.data;
			
		})
	}
	
	$scope.orderProperty = 'id';

	$scope.sort = function() {

	}

	$scope.currentPage = 0;
	$scope.pageSize = "10";

	$scope.totalQuantity = function() {
		return $scope.orders.length;
	}

	
	$scope.numberOfPages = function() {
		return Math.ceil($scope.orders.length / $scope.pageSize);
	}
	
	$scope.pagination = function() {
		$scope.currentPage = 0;
	}

	$scope.orderStatusReport = [];
	function getOrderCountPerStatus(year) {

		$scope.orderStatusDataTable = [];
		$http({
			url: "/api/report/ordetStatusReport/tableData",
			method: "GET",
			params: {
				year: year
			}
		}).then(resp => {
			$scope.orderStatusDataTable = resp.data;
		})

		var doughnut = document.getElementById("doChart");
		let apiUrl = "/api/report/getOrderCountByYearAndStatus";
		$http({
			url: apiUrl,
			method: "GET",
			params: {
				year: year
			}
		}).then(resp => {
			$scope.orderStatusReport = resp.data;
			if (doughnut !== null) {
				var myDoughnutChart = new Chart(doughnut, {
					type: "doughnut",
					data: {
						labels: ["Chờ xác nhận", "Chuẩn bị giao", "Đã xuất kho", "Đang vận chuyển", "Đã giao hàng", "Huỷ bỏ"],
						datasets: [
							{
								label: ["Chờ xác nhận", "Chuẩn bị giao", "Đã xuất kho", "Đang vận chuyển", "Đã giao hàng", "Huỷ bỏ"],
								data: [$scope.orderStatusReport[0], $scope.orderStatusReport[1], $scope.orderStatusReport[2], $scope.orderStatusReport[3], $scope.orderStatusReport[4], $scope.orderStatusReport[5]],
								backgroundColor: ["#4c84ff", "#ffa128", "#7be6ff", "#8061ef", "#80e1c1", "#ff7b7b"],
								borderWidth: 1
								// borderColor: ['#88aaf3','#29cc97','#8061ef','#fec402']
								// hoverBorderColor: ['#88aaf3', '#29cc97', '#8061ef', '#fec402']
							}
						]
					},
					options: {
						responsive: true,
						maintainAspectRatio: false,
						legend: {
							display: false
						},
						cutoutPercentage: 75,
						tooltips: {
							callbacks: {
								title: function(tooltipItem, data) {
									return "Order : " + data["labels"][tooltipItem[0]["index"]];
								},
								label: function(tooltipItem, data) {
									return data["datasets"][0]["data"][tooltipItem["index"]];
								}
							},
							titleFontColor: "#888",
							bodyFontColor: "#555",
							titleFontSize: 12,
							bodyFontSize: 14,
							backgroundColor: "rgba(256,256,256,0.95)",
							displayColors: true,
							borderColor: "rgba(220, 220, 220, 0.9)",
							borderWidth: 2
						}
					}
				});
			}
		})
	}
	$scope.yearValue = 2022;
	getOrderCountPerStatus(2022);
	$scope.showReport = function() {
		getOrderCountPerStatus($scope.yearValue);
	}
	
})
