const adminApp = angular.module("admin-app",["ngRoute"])
adminApp.config(function($routeProvider) {
	$routeProvider
	.when("/tco-admin/user/list", {
		templateUrl: " ",
		controller: "user-list-ctrl"
	})
	.when("/tco-admin/user/add", {
		templateUrl: " ",
		controller: "user-management-ctrl"
	})
	.when("/tco-admin/product/add", {
		templateUrl: " ",
		controller: "product-ctrl"
	})
	.when("/tco-admin/product/list", {
		templateUrl: " ",
		controller: "product-ctrl"
	})
	.when("/tco-admin/product/grid", {
		templateUrl: " ",
		controller: "product-ctrl"
	})
	.when("/tco-admin/category", {
		templateUrl: " ",
		controller: "category-ctrl"
	})
	.when("/tco-admin/order", {
		templateUrl: " ",
		controller: "order-ctrl"
	})
	.when("/tco-admin/dashboard", {
		templateUrl: " ",
		controller: "dashboard-ctrl"
	})
	.when("/tco-admin/review", {
		templateUrl: " ",
		controller: "review-ctrl"
	})
	.when("/tco-admin/transaction", {
		templateUrl: " ",
		controller: "transaction-ctrl"
	})
})
