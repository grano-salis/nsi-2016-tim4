APP.service('authService', ['$http', '$location', function($http, $location) {

    var service = this;

    service.loginModel = {
        Username: "mkoljenovic1",
		Password: "LaRambla1"
    };

    service.isLoggedIn = false;

    service.authUrl = 'http://do.mac.ba:8888/BusinessLogic/Account.svc/json/login';


    service.login = function (loginModel) {

        return $http.post(service.authUrl, JSON.stringify(loginModel));
    };

    service.logout = function () {
        service.clearToken();
        service.isLoggedIn = false;
        $location.path('/login');
    };

    service.getToken = function () {
        
        return localStorage.getItem('jwt');
    };

    service.clearToken = function () {
        
        if (localStorage.getItem('jwt')) {
            localStorage.removeItem('jwt');
        }
    }
}]);