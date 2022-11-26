adminApp.controller("order-status-report-ctrl", function($http, $scope) {
	
	$scope.orders = [];
	$http.get("/api/orders").then(resp => {
		$scope.orders = resp.data;
	})
	$scope.ordersDetail = [];
	$http.get("/api/ordersDetail").then(resp => {
		$scope.ordersDetail = resp.data;
	})
	
	$scope.orderStatusReport = [];
  function getOrderCountPerStatus(year, monthFrom, monthTo) {
	
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
	let apiUrl = "http://localhost:8080/api/report/getordercountperstatus";
	$http({
		url: apiUrl,
		method: "GET",
		params: {
			year: year,
			monthFrom: monthFrom,
			monthTo: monthTo
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
  getOrderCountPerStatus(2022,11,11);
})