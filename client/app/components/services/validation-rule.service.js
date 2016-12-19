APP.service('validationRuleService', ['$http', function($http) {

    var service = this;

    service.validationRuleUrl = BASE_API_URL + 'data/validation_rule/';

    service.getAllValidationRules = function () {

        return $http.get(BASE_API_URL + 'data/validation_rule');
    };
}]);