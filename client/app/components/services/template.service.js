APP.service('templateService', ['$http', function($http) {

    var service = this;

    service.templateUrl = BASE_API_URL + 'data/template/';

    service.insertValidationForPlaceholders = function (templateId, placeholderValidation) {

        return $http.post(service.templateUrl + templateId + '/placeholders/validationrules', JSON.stringify(placeholderValidation));
    };

    service.updateForMultiplePlaceholders = function (templateId, placeholderValidation) {

        return $http.put(service.templateUrl + templateId + '/placeholders/validationrules', JSON.stringify(placeholderValidation));
    };

    service.fillTemplate = function (templateId, placeholderObject) {
        return $http.post(service.templateUrl + templateId + '/fill', { placeholders: placeholderObject }, { responseType: 'arraybuffer' });
    };

    service.getTemplate = function (id) {

        return $http.get(BASE_API_URL + 'data/template/' + id);
    };

    service.getAllTemplates = function () {

        return $http.get(BASE_API_URL + 'data/template');
    };

    service.insertTemplate = function (template, file) {

        return $http({
                        url: BASE_API_URL + 'data/template',
                        method: "POST",
                        headers: { "Content-Type": undefined },
                        transformRequest: function(data) {
                            var formData = new FormData();
                            formData.append("template", JSON.stringify(data.template));
                            formData.append("file", data.file);
                            return formData;
                        },
                        data: {
                            template: template,
                            file: file
                        }
                    });
    };

    service.getTemplatePlaceholders = function (templateId) {

        return $http.get(service.templateUrl + templateId + '/placeholders');
    }
}]);