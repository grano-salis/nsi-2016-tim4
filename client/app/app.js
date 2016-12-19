'use strict';

// Declare app level module which depends on views, and components
var APP = angular.module('myApp', [
  'ngRoute',
  'myApp.view1',
  'myApp.view2',
  'myApp.version',
  'myApp.templateTypes',
  'myApp.validationRules',
  'myApp.auth'
]);

APP.run(['$rootScope', '$location', 'authService', function ($rootScope, $location, authService) {
        $rootScope.$on('$routeChangeStart', function (event) {
          
        debugger;
        if (!authService.isLoggedIn && !($location.path() === '/login')) {
          console.log('DENY : Redirecting to Login');
          event.preventDefault();
          $location.path('/login');
        }
        else if (authService.isLoggedIn && ($location.path() === '/login')) {
          $location.path('/templates');
        } else {
          console.log('allow');
        }
      })
}]);

APP.config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
	
  $routeProvider.otherwise({redirectTo: '/login'});
}]);

APP.controller('IndexCtrl', ['authService', '$scope', function (authService, $scope) {

    $scope.auth = authService;
    console.log($scope.auth);

    $scope.logout = function () {

      authService.logout();
    };
}]);