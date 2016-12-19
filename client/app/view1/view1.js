'use strict';

angular.module('myApp.view1', ['ngRoute'])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/templates', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl'
  });
  $routeProvider.when('/template/new', {
  	templateUrl: 'view1/add-template.html',
    controller: 'AddTemplateCtrl'
  });
  $routeProvider.when('/template/:id/fill', {
  	templateUrl: 'view1/fill-template.html',
    controller: 'FillTemplateCtrl'
  });
  $routeProvider.when('/template/:id/validation/add', {
  	templateUrl: 'view1/add-placeholder-validation.html',
    controller: 'AddValidationCtrl'
  });
  $routeProvider.when('/template/:id/validation/edit', {
  	templateUrl: 'view1/edit-placeholder-validation.html',
    controller: 'EditValidationCtrl'
  });
}])
.controller('View1Ctrl', ['$scope', '$location', 'templateService', function($scope, $location, templateService) {

	$scope.templates = [];
	$scope.loading = true;

	templateService.getAllTemplates()
				   .then(function (data) {
						$scope.templates = data.data.data;
						$scope.loading = false;
						console.log($scope.templates);
				    }, function (error) {
						$scope.loading = false;
					});
}])
.controller('AddTemplateCtrl', ['$scope', '$location', '$http', 'templateService', function($scope, $location, $http, templateService) {

	$scope.template = {
		name: '',
		templateDefinition: {
			templateType: {
				id: null
			}
		}
	};
	$scope.file = null;

	$scope.templateTypes = [];
	$scope.loading = true;

	$http.get('http://localhost:8081/ReportTemplateEngine/api/services/data/template_type')
		 .then(function (data) {
		 	$scope.templateTypes = data.data.data;
		 	$scope.loading = false;
		 }, function (error) {
		 	$scope.loading = false;
		 });

	$scope.saveTemplate = function () {

		$scope.loading = true;
		templateService.insertTemplate($scope.template, $scope.file)
					   .success(function(response) {
							$location.path('/templates');
							$scope.loading = false;
					   })
					   .error(function (errorResponse) {
							$scope.loading = false;
					   });
            
	};
}])
.controller('FillTemplateCtrl', ['$scope', '$location', '$routeParams', '$window', 'templateService', function($scope, $location, $routeParams, $window, templateService) {

	$scope.templateId = null;
	$scope.placeholders = [];
	$scope.loading = true;

	$scope.templateId = $routeParams.id;
	
	templateService.getTemplatePlaceholders($scope.templateId)
				   .then(function (data) {
						$scope.placeholders = data.data.data;
						templateService.getTemplate($scope.templateId)
										.then(function (response) {

											$scope.placeholderValidation = $scope.placeholders
																	.map(function (placeholder) {
																		var placeholderValidationRules =  response.data.data
																												  .templateDefinition
																												  .validationRules
																												  .filter(function (item) { return item.PLACEHOLDERID == placeholder.id; })
																												  .map(function (item) { return { value: item.VALUE }; });
																		return {
																			placeholder: placeholder,
																			validationRules: placeholderValidationRules
																		}
																	});
																	$scope.loading = false;
										});
				   }, function (error) {
						$scope.loading = false;
				   });

	$scope.getRegexes = function (placeholderId) {

		return $scope.placeholderValidation.find(function (item) { return item.placeholder.id === placeholderId; })
												  .validationRules;
	};

	$scope.fillTemplate = function () {

		var placeholderObject = {};
		$scope.placeholders.forEach(function (placeholder) {
			placeholderObject[placeholder.name] = placeholder.value;
		});
		$scope.loading = true;
		templateService.fillTemplate($scope.templateId, placeholderObject)
					   .then(function (data) {

							var file = new Blob([data.data], {type: 'application/pdf'});
							var fileURL = URL.createObjectURL(file);
							$window.open(fileURL);
							$scope.loading = false;
							$location.path('/templates');
					   }, function (error) {
							$scope.loading = false;
							console.log(error);
					   });
			 
	};
}])
.controller('EditValidationCtrl', ['$scope', '$location', '$routeParams', 'templateService', 'validationRuleService', function ($scope, $location, $routeParams, templateService, validationRuleService) {

	var ctrl = this;

	$scope.templateId = null;
	$scope.placeholders = [];
	$scope.validationRules = [];
	$scope.ruleDictionary = {};
	$scope.currentValidationRules = [];
	$scope.placeholderValidation = [];
	$scope.loading = true;

	$scope.templateId = $routeParams.id;

	Promise.all([
		templateService.getTemplate($scope.templateId),
		validationRuleService.getAllValidationRules()
	])
	.then(function (responses) {

		$scope.placeholders = responses[0].data.data.templateDefinition.placeholders;
		$scope.currentValidationRules = responses[0].data.data.templateDefinition.validationRules;
		$scope.validationRules = responses[1].data.data;
		$scope.validationRules.unshift(null);
		$scope.validationRules.forEach(function (item) {
			if (item) {
				$scope.ruleDictionary[item.id.toString()] = item.name;
			}
		});
		// prepare validationObjects
		$scope.placeholderValidation = $scope.placeholders
											 .map(function (placeholder) {
												 var placeholderValidationRules =  $scope.currentValidationRules
												 										 .filter(function (item) { return item.PLACEHOLDERID == placeholder.id; })
																						 .map(function (item) { return { rowId: item.ROW_ID, ruleId: item.ID }; });
												 return {
													 placeholder: placeholder,
													 validationRules: placeholderValidationRules
												 }
											 });
		$scope.loading = false;
		$scope.$apply();
	}, function (error) {
		$scope.loading = false;
	});


	/**
	 * Add empty rule by passing a placeholder id
	 */
	$scope.addEmptyRule = function (placeholderId) {

		$scope.placeholderValidation
			  .find(function (item) { return item.placeholder.id === placeholderId; })
			  .validationRules
			  .push(null);
	};

	/**
	 * Remove existing rule by providing placeholderId, rule instance
	 * if rule is null, delete by index
	 */
	$scope.removeRule = function (placeholderId, rule, index) {

		var placeholder = $scope.placeholderValidation
			  					.find(function (item) { return item.placeholder.id === placeholderId; });
		if (rule) {
			placeholder.validationRules.splice(placeholder.validationRules.indexOf(rule), 1);
			return;
		}
		placeholder.validationRules.splice(index, 1);
	};

	/**
	 * Save rules
	 */
	$scope.addRules = function () {

		$scope.loading = true;
		var apiObject = {
			placeholderValidation: [].concat.apply([], $scope.placeholderValidation
															 .map(function (item) {
																
															 	return item.validationRules
															    		   .map(function (rule) {
																				return {
																					placeholder_id: item.placeholder.id || null,
																					validation_rule_id: rule.ruleId ? +rule.ruleId : null,
																					row_id: rule.rowId
																				}
																		    });
															  }))
		};
		debugger;
		templateService.updateForMultiplePlaceholders($scope.templateId, apiObject)
					   .then(function (response) {
						   
						   $scope.currentValidationRules = response.data.data.templateDefinition.validationRules;
						   $scope.placeholderValidation = $scope.placeholders
																.map(function (placeholder) {
																	var placeholderValidationRules =  $scope.currentValidationRules
																											.filter(function (item) { return item.PLACEHOLDERID == placeholder.id; })
																											.map(function (item) { return { rowId: item.ROW_ID, ruleId: item.ID }; });
																	return {
																		placeholder: placeholder,
																		validationRules: placeholderValidationRules
																	}
																});
						   $scope.loading = false;
					   }, function (error) {
						   $scope.loading = false;
					   });
	};

	$scope.onRuleChange = function (ruleIndex, newValue, pval) {

		var pvalidate = $scope.placeholderValidation
			  				  .find(function (item) { return item.placeholder.id === pval.placeholder.id; });

		pvalidate.validationRules
				 .forEach(function (item, index) {

					 if (index !== ruleIndex && +item.ruleId === +newValue) {
				 		 pvalidate.validationRules[index].ruleId = null;
				 	 }
				 });
	};
}])
.controller('AddValidationCtrl', ['$scope', '$location', '$routeParams', 'templateService', 'validationRuleService', function ($scope, $location, $routeParams, templateService, validationRuleService) {

	var ctrl = this;

	$scope.templateId = null;
	$scope.placeholders = [];
	$scope.validationRules = [];
	$scope.ruleDictionary = {};
	$scope.currentValidationRules = [];
	$scope.placeholderValidation = [];
	$scope.loading = true;

	$scope.templateId = $routeParams.id;

	Promise.all([
		templateService.getTemplate($scope.templateId),
		validationRuleService.getAllValidationRules()
	])
	.then(function (responses) {

		$scope.placeholders = responses[0].data.data.templateDefinition.placeholders;
		$scope.validationRules = responses[1].data.data;
		$scope.validationRules.forEach(function (item) {
			if (item) {
				$scope.ruleDictionary[item.id.toString()] = item.name;
			}
		});
		responses[0].data.data
					.templateDefinition
					.validationRules
					.forEach(function (item) {
						var placeholderString = item.PLACEHOLDERID.toString();
						if ($scope.currentValidationRules[placeholderString]) {
							$scope.currentValidationRules[placeholderString].rules.push(item.ID); 
						} else {
							$scope.currentValidationRules[placeholderString] = { rules: [] };
							$scope.currentValidationRules[placeholderString].rules.push(item.ID);
						}
					});

		// prepare validationObjects
		$scope.placeholderValidation = $scope.placeholders
											 .map(function (placeholder) {
												 return {
													 placeholder: placeholder,
													 validationRules: []
												 }
											 });
											 
		$scope.loading = false;
		$scope.$apply();
	}, function (error) {
		$scope.loading = false;
	});


	/**
	 * Add empty rule by passing a placeholder id
	 */
	$scope.addEmptyRule = function (placeholderId) {

		$scope.placeholderValidation
			  .find(function (item) { return item.placeholder.id === placeholderId; })
			  .validationRules
			  .push(null);
	};

	/**
	 * Remove existing rule by providing placeholderId, rule instance
	 * if rule is null, delete by index
	 */
	$scope.removeRule = function (placeholderId, rule, index) {

		var placeholder = $scope.placeholderValidation
			  					.find(function (item) { return item.placeholder.id === placeholderId; });
		if (rule) {
			placeholder.validationRules.splice(placeholder.validationRules.indexOf(rule), 1);
			return;
		}
		placeholder.validationRules.splice(index, 1);
	};

	/**
	 * Save rules
	 */
	$scope.addRules = function () {

		$scope.loading = true;
		var apiObject = {
			placeholderValidation: [].concat.apply([], $scope.placeholderValidation
															 .map(function (item) {
																
															 	return item.validationRules
															    		   .map(function (rule) {
																				return {
																					placeholder_id: item.placeholder.id || null,
																					validation_rule_id: rule ? +rule : null
																				}
																		    });
															  }))
		};
		templateService.insertValidationForPlaceholders($scope.templateId, apiObject)
					   .then(function (response) {
						   
						   $scope.loading = false;
						   $location.path('/template/' + $scope.templateId + '/validation/edit');
					   }, function (error) {
						   $scope.loading = false;
					   });
	};

	$scope.onRuleChange = function (ruleIndex, newValue, pval) {

		if (!newValue) {
			return;
		}
		var pvalidate = $scope.placeholderValidation
			  				  .find(function (item) { return item.placeholder.id === pval.placeholder.id; });
		pvalidate.validationRules[ruleIndex] = newValue;

		pvalidate.validationRules = pvalidate.validationRules
											 .filter(function (item, index, self) {
													return self.indexOf(item) === index;
											 });
	};
}]);