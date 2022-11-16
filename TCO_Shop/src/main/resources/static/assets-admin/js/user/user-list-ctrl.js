adminApp.controller("user-list-ctrl", function($scope, $http) {
    $scope.users = [];
 
    $http.get("/api/user").then(resp=> {
        $scope.users = resp.data;
    }) 
})