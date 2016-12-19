angular.module('myApp.templateTypes', ['ngRoute'])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/template_types', {
    templateUrl: 'template_types/template-type-index.html',
    controller: 'TemplateTypeIndexCtrl'
  });
  $routeProvider.when('/template_type/new', {
  	templateUrl: 'template_types/add-template-type.html',
    controller: 'AddTemplateTypeCtrl'
  });
  $routeProvider.when('/template_type/:id/detail', {
  	templateUrl: 'template_types/template-type-detail.html',
    controller: 'TemplateTypeDetailCtrl'
  });
  $routeProvider.when('/template_type/:id', {
    templateUrl: 'template_types/template-type-edit.html',
    controller: 'TemplateTypeEditCtrl'
  });
}])
.controller('TemplateTypeIndexCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {

	$scope.templateTypes = [];
	$scope.loading = true;

	$http.get('http://localhost:8081/ReportTemplateEngine/api/services/data/template_type')
		 .then(function (data) {
		 	$scope.templateTypes = data.data.data;
		 	$scope.loading = false;
		 }, function (error) {
		 	$scope.loading = false;
		 });

  $scope.deleteTemplateType = function (id) {

    $http.delete('http://localhost:8081/ReportTemplateEngine/api/services/data/template_type/' + id)
     .then(function (data) {
      var deleted = data.data.data;
      $scope.templateTypes.splice($scope.templateTypes.findIndex(function (item) { return item.id === deleted.id; }), 1);
      $scope.loading = false;
     }, function (error) {
      $scope.loading = false;
     });
  };

}])
.controller('AddTemplateTypeCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {

  $scope.templateType = {
    name: ''
  };
  $scope.loading = false;

  $scope.saveTemplateType = function () {

    $scope.loading = true;
    $http.post('http://localhost:8081/ReportTemplateEngine/api/services/data/template_type', $scope.templateType)
         .then(function (data) {
          $scope.loading = false;
          $location.path('/template_types');
         }, function (error) {
          $scope.loading = false;
         });
  };
}])
.controller('TemplateTypeDetailCtrl', ['$scope', '$http', '$location', '$routeParams', function($scope, $http, $location, $routeParams) {

  $scope.templateType = {};
  $scope.loading = true;

  $http.get('http://localhost:8081/ReportTemplateEngine/api/services/data/template_type/' + $routeParams.id)
     .then(function (data) {
      $scope.templateType = data.data.data;
      $scope.loading = false;
     }, function (error) {
      $scope.loading = false;
     });
}])
.controller('TemplateTypeEditCtrl', ['$scope', '$http', '$location', '$routeParams', function($scope, $http, $location, $routeParams) {

  $scope.templateType = {
    name: ''
  };
  
  $scope.loading = true;

  $http.get('http://localhost:8081/ReportTemplateEngine/api/services/data/template_type/' + $routeParams.id)
     .then(function (data) {
      $scope.templateType = data.data.data;
      $scope.loading = false;
     }, function (error) {
      $scope.loading = false;
     });

  $scope.saveTemplateType = function () {

    $scope.loading = true;
    $http.put('http://localhost:8081/ReportTemplateEngine/api/services/data/template_type', JSON.stringify($scope.templateType))
         .then(function (data) {
          $scope.loading = false;
          $location.path('/template_types');
         }, function (error) {
          $scope.loading = false;
         });
  };
}]);