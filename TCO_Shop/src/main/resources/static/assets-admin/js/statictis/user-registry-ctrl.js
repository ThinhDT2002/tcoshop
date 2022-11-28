adminApp.controller("user-registry-ctrl", function($http, $scope) {
	$scope.userRegisterPerYear = [];
	$scope.userRegistryReport = [];
	$scope.userRegistryDetailReport = [];
	function getUserRegistryDetailReport(year, month) {
		$http({
			url: "/api/report/userRegistryDetailReport",
			method: "GET",
			params: {
				year: year,
				month: month
			}
		}).then(resp => {
			$scope.userRegistryDetailReport = resp.data;
			console.log($scope.userRegistryDetailReport);
		})
	}
  function getUserRegisterPerYear(year) {
	$http({
		url: "/api/report/userRegistryReport",
		method: "GET",
		params: {
			year: year
		}
	}).then(resp => {
		$scope.userRegistryReport = resp.data;
		console.log($scope.userRegistryReport);
	})
	
    var cUser = document.getElementById("currentUser");
	let apiUrl = "http://localhost:8080/api/report/getuserregister";
	$http({
		url: apiUrl,
		method: "GET",
		params: {
			year: year
		}
	}).then(resp => {
		$scope.userRegisterPerYear = resp.data;
    if (cUser !== null) {
      var myUChart = new Chart(cUser, {
        type: "bar",
        data: {
          labels: [
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12"
          ],
          datasets: [
            {
              label: "signup",
              data: [$scope.userRegisterPerYear[0], $scope.userRegisterPerYear[1], $scope.userRegisterPerYear[2], $scope.userRegisterPerYear[3], $scope.userRegisterPerYear[4], $scope.userRegisterPerYear[5], $scope.userRegisterPerYear[6], $scope.userRegisterPerYear[7], $scope.userRegisterPerYear[8], $scope.userRegisterPerYear[9], $scope.userRegisterPerYear[10], $scope.userRegisterPerYear[11]],
              // data: [2, 3.2, 1.8, 2.1, 1.5, 3.5, 4, 2.3, 2.9, 4.5, 1.8, 3.4, 2.8],
              backgroundColor: "#88aaf3"
            }
          ]
        },
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
                  drawBorder: true,
                  display: false,
                },
                ticks: {
                  fontColor: "#8a909d",
                  fontFamily: "Roboto, sans-serif",
                  display: false, // hide main x-axis line
                  beginAtZero: true,
                  callback: function(tick, index, array) {
                    return index % 2 ? "" : tick;
                  }
                },
                barPercentage: 1.8,
                categoryPercentage: 0.2
              }
            ],
            yAxes: [
              {
                gridLines: {
                  drawBorder: true,
                  display: true,
                  color: "#eee",
                  zeroLineColor: "#eee"
                },
                ticks: {
                  fontColor: "#8a909d",
                  fontFamily: "Roboto, sans-serif",
                  display: true,
                  beginAtZero: true
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
            xPadding: 10,
            yPadding: 7,
            borderColor: "rgba(220, 220, 220, 0.9)",
            borderWidth: 2,
            caretSize: 6,
            caretPadding: 5
          }
        }
      });
    }
	})
}
getUserRegisterPerYear(2022);
getUserRegistryDetailReport(2022, 4);
})