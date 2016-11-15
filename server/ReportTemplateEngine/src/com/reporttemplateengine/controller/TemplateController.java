package com.reporttemplateengine.controller;

import java.util.ArrayList;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reporttemplateengine.dao.ICrud;
import com.reporttemplateengine.helpers.ApiResponse;
import com.reporttemplateengine.helpers.Constants;
import com.reporttemplateengine.models.Template;

@RestController
public class TemplateController {

	@Autowired
    private ICrud<Template> dao;

	@RequestMapping(value = "/api/services/data/template", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getAll() {

		try {
			List<Template> templateTypes = this.dao.getAll();
			if (templateTypes == null) {
				return new ApiResponse(new ArrayList<Template>()).send(HttpStatus.OK);
			}
			return new ApiResponse(templateTypes).send(HttpStatus.OK);
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template/{id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getAll(@PathVariable Integer id) {

		try {
			Template templateType = this.dao.getById(id);
			if (templateType == null) {
				return new ApiResponse().send(HttpStatus.NOT_FOUND, Constants.TEMPLATE_NOT_FOUND);
			}
			return new ApiResponse(templateType).send(HttpStatus.OK);
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> insert(@Valid @RequestBody Template templateType, Errors errors) {

		if (errors.hasErrors()) {
			return new ApiResponse().send(HttpStatus.BAD_REQUEST);
		}
		
		try {
			Template type = this.dao.insert(templateType);
			return new ApiResponse(type).send(HttpStatus.OK);
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse> update(@Valid @RequestBody Template templateType, Errors errors) {

		if (errors.hasErrors() || templateType.getId().equals(null)) {
			return new ApiResponse().send(HttpStatus.BAD_REQUEST);
		}
		
		Template type = this.dao.getById(templateType.getId());
		if (type == null) {
			return new ApiResponse().send(HttpStatus.NOT_FOUND, Constants.TEMPLATE_NOT_FOUND);
		}
		
		try {
			return new ApiResponse(this.dao.update(templateType)).send(HttpStatus.OK);
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {

		try {
			Template templateType = this.dao.getById(id);
			if (templateType == null) {
				return new ApiResponse().send(HttpStatus.NOT_FOUND, Constants.TEMPLATE_NOT_FOUND);
			}
			this.dao.delete(id);
			return new ApiResponse(templateType).send(HttpStatus.OK);
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
}
