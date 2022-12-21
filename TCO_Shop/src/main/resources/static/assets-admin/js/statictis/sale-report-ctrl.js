adminApp.controller("sale-report-ctrl", function($scope, $http) {
	$scope.allOrderYear = [];
	$scope.orders = [];
	$scope.ordersDetail = [];
	$http.get("/api/report/saleReport/allYearOrder").then(resp => {
		$scope.allOrderYear = resp.data;
	})
	$scope.monthOrderDetail;
	$scope.yearOrderDetail;
	$scope.getSaleReportDetail = function(year, month) {
		$scope.monthOrderDetail = month;
		$scope.yearOrderDetail = year;
		$http({
			url: "/api/report/saleReportDetail",
			method: "GET",
			params: {
				year: year,
				month: month
			}
		}).then(resp => {
			$scope.orders = resp.data;			
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

	function getSalesReport(year) {
		$scope.saleReportTableData = [];
		$http({
			url: "/api/report/saleReport/tableData",
			method: "GET",
			params: {
				year: year
			}
		}).then(resp => {
			$scope.saleReportTableData = resp.data;
		})
		var report = new Object();
		var acquisition = document.getElementById("acquisition");
		$http.get(`http://localhost:8080/api/report/getsalesreport/${year}`).then(resp => {
			if (acquisition !== null) {
				report = resp.data
				var acqData = [
					{
						first: [0, report[0], report[1], report[2], report[3], report[4], report[5]],
					}
				];
				var maxSize = Math.max(...report);
				var reportStep = maxSize / 5;
				var configAcq = {
					// The type of chart we want to create
					type: "line",
					// The data for our dataset
					data: {
						labels: [
							"0",
							"2",
							"4",
							"6",
							"8",
							"10",
							"12 (Tháng)"
						],
						datasets: [
							{
								label: "Đơn hàng",
								backgroundColor: "rgba(52, 116, 212, .2)",
								borderColor: "rgba(52, 116, 212, .7)",
								data: acqData[0].first,
								lineTension: 0.3,
								pointBackgroundColor: "rgba(52, 116, 212,0)",
								pointHoverBackgroundColor: "rgba(52, 116, 212,1)",
								pointHoverRadius: 3,
								pointHitRadius: 30,
								pointBorderWidth: 2,
								pointStyle: "rectRounded"
							},
						]
					},
					// Configuration options go here
					options: {
						responsive: true,
						maintainAspectRatio: false,
						legend: {
							display: false
						},
						scales: {
							xAxes: [
								{
									gridLines: {
										display: false
									}
								}
							],
							yAxes: [
								{
									gridLines: {
										display: true,
										color: "#eee",
										zeroLineColor: "#eee"
									},
									ticks: {
										beginAtZero: true,
										stepSize: reportStep,
										max: maxSize
									}
								}
							]
						},
						tooltips: {
							mode: "index",
							titleFontColor: "#888",
							bodyFontColor: "#555",
							titleFontSize: 12,
							bodyFontSize: 15,
							backgroundColor: "rgba(256,256,256,0.95)",
							displayColors: true,
							xPadding: 20,
							yPadding: 10,
							borderColor: "rgba(220, 220, 220, 0.9)",
							borderWidth: 2,
							caretSize: 10,
							caretPadding: 15
						}
					}
				};
				var ctx = document.getElementById("acquisition").getContext("2d");
				var lineAcq = new Chart(ctx, configAcq);
				document.getElementById("acqLegend").innerHTML = lineAcq.generateLegend();
				var items = document.querySelectorAll(
					"#user-acquisition .nav-tabs .nav-item"
				);
				items.forEach(function(item, index) {
					item.addEventListener("click", function() {
						configAcq.data.datasets[0].data = acqData[index].first;
						configAcq.data.datasets[1].data = acqData[index].second;
						configAcq.data.datasets[2].data = acqData[index].third;
						lineAcq.update();
					});
				});
			}
			report = resp.data;
			return report;
		})
	}
	$scope.yearValue = 2022;
	getSalesReport($scope.yearValue);
	$scope.showReport = function() {
		getSalesReport($scope.yearValue);
	}
})