adminApp.controller("turnover-report-ctrl", function($http, $scope) {
	$scope.allOrderYear = [];
	$http.get("/api/report/saleReport/allYearOrder").then(resp => {
		$scope.allOrderYear = resp.data;
	})
	// Tồn kho start
	$scope.productsStock = [];
	$http.get("/api/products").then(resp => {
		$scope.productsStock = resp.data;
	})

	$scope.productStockProperty = 'name';

	$scope.productStockSort = function() {

	}

	$scope.currentPageProductStock = 0;
	$scope.pageSizeProductStock = "10";

	$scope.numberOfPagesProductStock = function() {
		return Math.ceil($scope.productsStock.length / $scope.pageSizeProductStock);
	}

	$scope.paginationProductStock = function() {
		$scope.currentPageProductStock = 0;
	}
	// Tồn kho end
	//Not sold start
	$scope.productsNotSold = [];
	var today = new Date();
	var month = String(today.getMonth() + 1).padStart(2, '0');
	var year = today.getFullYear();
	$scope.yearProductNotSold;
	$scope.monthProductNotSold;
	
	
	$http({
		
		url: "/api/report/productNotSoldInMonth",
		method: "GET",
		params: {
			year: year,
			month: month
		}
	}).then(resp => {
		$scope.yearProductNotSold = year;
		$scope.monthProductNotSold = month;
		$scope.productsNotSold = resp.data;
	})
	$scope.productNotSoldProperty = 'name';

	$scope.productNotSoldSort = function() {

	}

	$scope.currentPageProductNotSold = 0;
	$scope.pageSizeProductNotSold = "10";

	$scope.numberOfPagesProductNotSold = function() {
		return Math.ceil($scope.productsNotSold.length / $scope.pageSizeProductNotSold);
	}

	$scope.paginationProductNotSold = function() {
		$scope.currentPageProductNotSold = 0;
	}
	//Not sold end

	$scope.turnoverPerYear = [];
	$scope.turnoverDetailReport = [];

	$scope.monthTurnoverDetail;
	$scope.yearTurnoverDetail;
	$scope.getTurnoverDetailReport = function(year, month) {
		$scope.monthTurnoverDetail = month;
		$scope.yearTurnoverDetail = year;
		$http({
			url: "/api/report/turnoverReport/detail",
			method: "GET",
			params: {
				year: year,
				month: month
			}
		}).then(resp => {
			$scope.turnoverDetailReport = resp.data;
		})
	}

	$scope.turnoverDetailProperty = 'productName';

	$scope.sort = function() {

	}

	$scope.currentPage = 0;
	$scope.pageSize = "10";

	$scope.numberOfPages = function() {
		return Math.ceil($scope.turnoverDetailReport.length / $scope.pageSize);
	}

	$scope.pagination = function() {
		$scope.currentPage = 0;
	}

	function getTurnoverPerYear(year) {
		$scope.turnoverReport = [];
		$http({
			url: "/api/report/turnoverReport/tableData",
			method: "GET",
			params: {
				year: year
			}
		}).then(resp => {
			$scope.turnoverReport = resp.data;
		})

		var activity = document.getElementById("activity");
		let apiUrl = "http://localhost:8080/api/report/getturnoverperyear";
		$http({
			url: apiUrl,
			method: "GET",
			params: {
				year: year
			}
		}).then(resp => {
			$scope.turnoverPerYear = resp.data;
			if (activity !== null) {
				var activityData = [
					{
						first: [0, $scope.turnoverPerYear[0], $scope.turnoverPerYear[1], $scope.turnoverPerYear[2], $scope.turnoverPerYear[3], $scope.turnoverPerYear[4], $scope.turnoverPerYear[5]],
					},
				];
				var maxTurnover = Math.max(...$scope.turnoverPerYear);
				var config = {
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
								label: "Active",
								backgroundColor: "transparent",
								borderColor: "rgba(82, 136, 255, .8)",
								data: activityData[0].first,
								lineTension: 0,
								pointRadius: 5,
								pointBackgroundColor: "rgba(255,255,255,1)",
								pointHoverBackgroundColor: "rgba(255,255,255,1)",
								pointBorderWidth: 2,
								pointHoverRadius: 7,
								pointHoverBorderWidth: 1
							},
							{
								label: "Inactive",
								backgroundColor: "transparent",
								borderColor: "rgba(255, 199, 15, .8)",
								data: activityData[0].second,
								lineTension: 0,
								borderDash: [10, 5],
								borderWidth: 1,
								pointRadius: 5,
								pointBackgroundColor: "rgba(255,255,255,1)",
								pointHoverBackgroundColor: "rgba(255,255,255,1)",
								pointBorderWidth: 2,
								pointHoverRadius: 7,
								pointHoverBorderWidth: 1
							}
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
										display: false,
									},
									ticks: {
										fontColor: "#8a909d", // this here
									},
								}
							],
							yAxes: [
								{
									gridLines: {
										fontColor: "#8a909d",
										fontFamily: "Roboto, sans-serif",
										display: true,
										color: "#eee",
										zeroLineColor: "#eee"
									},
									ticks: {
										// callback: function(tick, index, array) {
										//   return (index % 2) ? "" : tick;
										// }
										stepSize: maxTurnover / 4,
										fontColor: "#8a909d",
										fontFamily: "Roboto, sans-serif"
									}
								}
							]
						},
						tooltips: {
							mode: "index",
							intersect: false,
							titleFontColor: "#888",
							bodyFontColor: "#555",
							titleFontSize: 12,
							bodyFontSize: 15,
							backgroundColor: "rgba(256,256,256,0.95)",
							displayColors: true,
							xPadding: 10,
							yPadding: 7,
							borderColor: "rgba(220, 220, 220, 0.9)",
							borderWidth: 2,
							caretSize: 6,
							caretPadding: 5
						}
					}
				};

				var ctx = document.getElementById("activity").getContext("2d");
				var myLine = new Chart(ctx, config);

				var items = document.querySelectorAll("#user-activity .nav-tabs .nav-item");
				items.forEach(function(item, index) {
					item.addEventListener("click", function() {
						config.data.datasets[0].data = activityData[index].first;
						config.data.datasets[1].data = activityData[index].second;
						myLine.update();
					});
				});
			}
		})
	}
	$scope.yearValue = 2022;
	getTurnoverPerYear(2022);
	$scope.showReport = function() {
		getTurnoverPerYear($scope.yearValue);
	}

})