APP.directive("suiDropdown", function($timeout) {
    return {
        scope: {
            dropdownOptions: "<",
            value: "="
        },
        link: function(scope, element, attrs) {
            $(element).dropdown(scope.dropdownOptions);
            scope.$watch('value', function (newValue, oldValue) {
                if (!newValue) {
                    $timeout(function() {
                        $(element).dropdown('clear');
                    }, 0);
                }
            });
        }
    }
});