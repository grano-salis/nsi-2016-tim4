APP.directive('regexValidator', function (){ 
   return {
      require: 'ngModel',
      link: function(scope, elem, attr, ngModel) {

          ngModel.$validators.invalidRegex = function(modelValue, viewValue) {
            // validation logic here
            var isValid = true;
            try {
                new RegExp(modelValue);
            } catch(e) {
                isValid = false;
            }
            return isValid;
          }
      }
   };
});

APP.directive('multiplePattern', function (){ 
   return {
      require: 'ngModel',
      scope: {
          regexes: '='
      },
      link: function(scope, elem, attr, ngModel) {

          ngModel.$validators.invalidPatterns = function(modelValue, viewValue) {

            if (!scope.regexes || !scope.regexes.length) {
                return true;
            }

            if (!modelValue) {
                return false;
            }

            var isValid = true;
            for (var i = 0; i < scope.regexes.length; i++) {
                console.log("Regex: " + scope.regexes[i].value);
                console.log(modelValue);
                var exp = new RegExp(scope.regexes[i].value);
                if (!exp.test(modelValue)) {
                    isValid = false;
                    break;
                }
            }
            return isValid;
          }
      }
   };
});