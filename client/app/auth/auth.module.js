angular.module('myApp.auth', ['ngRoute'])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'auth/login.html',
    controller: 'LoginCtrl'
  });
}])
.controller('LoginCtrl', ['$scope', '$http', '$location', 'authService', function($scope, $http, $location, authService) {

    var ctrl = this;

    $scope.errorMessage = "";
    $scope.loading = false;
    $scope.submitted = false;
    $scope.credentials = {
        loginModel: {
            Username: "",
            Password: ""
        }
    };

    $scope.attemptLogin = function () {

        $scope.loading = true;
        $scope.submitted = false;
        $scope.errorMessage = '';
        authService.login($scope.credentials)
                   .then(function (response) {
                       $scope.loading = false;
                       $scope.submitted = true;
                       authService.isLoggedIn = true;
                       $location.path('/templates');
                   }, function (error) {
                       $scope.submitted = true;
                       $scope.loading = false;
                       $scope.errorMessage = 'Wrong login credentials!';
                   });
    };
}]);