APP.filter('exceptValues', function() {

  return function(input, key, except) {

    input = input || [];

    if (!except) {
        return input;
    }
    except = [].concat(except);
    var values = except.filter(function (ex) {
        return ex !== null;
    }).map(function (ex) {
        if (ex.hasOwnProperty(key)) {
            return ex[key].toString();
        }
        return ex.toString();
    })
    .filter(function (item, index, self) {
                   return self.indexOf(item) === index;
               });

    return input.filter(function (item) {
        return item !== null && values.indexOf(item[key].toString()) === -1;
    });
  }

});