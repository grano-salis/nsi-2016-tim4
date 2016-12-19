APP.directive("suiAccordion", function() {
    return {
        scope: {
            accordionOptions: "<"
        },
        link: function(scope, element, attrs) {
            $(element).accordion();
        }
    }
});