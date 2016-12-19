angular.module('myApp.validationRules', ['ngRoute'])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/validation_rules', {
    templateUrl: 'validation_rules/validation-rule-index.html',
    controller: 'ValidationRuleIndexCtrl'
  });
  $routeProvider.when('/validation_rule/new', {
  	templateUrl: 'validation_rules/validation-rule-add.html',
    controller: 'AddValidationRuleCtrl'
  });
  $routeProvider.when('/validation_rule/:id/detail', {
  	templateUrl: 'validation_rules/validation-rule-detail.html',
    controller: 'ValidationRuleDetailCtrl'
  });
  $routeProvider.when('/validation_rule/:id', {
    templateUrl: 'validation_rules/validation-rule-edit.html',
    controller: 'ValidationRuleEditCtrl'
  });
}])
.controller('ValidationRuleIndexCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {

	$scope.validationRules = [];
	$scope.loading = true;

	$http.get('http://localhost:8081/ReportTemplateEngine/api/services/data/validation_rule')
		 .then(function (data) {
		 	$scope.validationRules = data.data.data;
		 	$scope.loading = false;
		 }, function (error) {
		 	$scope.loading = false;
		 });

  $scope.deleteValidationRule = function (id) {

    $http.delete('http://localhost:8081/ReportTemplateEngine/api/services/data/validation_rule/' + id)
     .then(function (data) {
      var deleted = data.data.data;
      $scope.validationRules.splice($scope.validationRules.findIndex(function (item) { return item.id === deleted.id; }), 1);
      $scope.loading = false;
     }, function (error) {
      $scope.loading = false;
     });
  };

}])
.controller('AddValidationRuleCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {

  $scope.validationRule = {
    name: '',
    value: ''
  };
  $scope.loading = false;

  $scope.saveValidationRule = function () {

    $scope.loading = true;
    $http.post('http://localhost:8081/ReportTemplateEngine/api/services/data/validation_rule', $scope.validationRule)
         .then(function (data) {
          $scope.loading = false;
          $location.path('/validation_rules');
         }, function (error) {
          $scope.loading = false;
         });
  };
}])
.controller('ValidationRuleDetailCtrl', ['$scope', '$http', '$location', '$routeParams', function($scope, $http, $location, $routeParams) {

  $scope.validationRule = {};
  $scope.loading = true;

  $http.get('http://localhost:8081/ReportTemplateEngine/api/services/data/validation_rule/' + $routeParams.id)
     .then(function (data) {
      $scope.validationRule = data.data.data;
      $scope.loading = false;
     }, function (error) {
      $scope.loading = false;
     });
}])
.controller('ValidationRuleEditCtrl', ['$scope', '$http', '$location', '$routeParams', function($scope, $http, $location, $routeParams) {

  $scope.validationRule = {
    name: '',
    value: ''
  };
  
  $scope.loading = true;

  $http.get('http://localhost:8081/ReportTemplateEngine/api/services/data/validation_rule/' + $routeParams.id)
     .then(function (data) {
      $scope.validationRule = data.data.data;
      $scope.loading = false;
     }, function (error) {
      $scope.loading = false;
     });

  $scope.saveValidationRule = function () {

    $scope.loading = true;
    $http.put('http://localhost:8081/ReportTemplateEngine/api/services/data/validation_rule', JSON.stringify($scope.validationRule))
         .then(function (data) {
          $scope.loading = false;
          $location.path('/validation_rules');
         }, function (error) {
          $scope.loading = false;
         });
  };
}]);