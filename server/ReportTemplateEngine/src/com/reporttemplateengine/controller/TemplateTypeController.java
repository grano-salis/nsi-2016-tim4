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
import com.reporttemplateengine.models.TemplateType;
import com.sun.media.jfxmedia.logging.Logger;

@RestController
public class TemplateTypeController {

	@Autowired
    private ICrud<TemplateType> dao;

	@RequestMapping(value = "/api/services/data/template_type", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getAll() {

		try {
			List<TemplateType> templateTypes = this.dao.getAll();
			if (templateTypes == null) {
				return new ApiResponse(new ArrayList<TemplateType>()).send(HttpStatus.OK);
			}
			return new ApiResponse(templateTypes).send(HttpStatus.OK);
		} catch (Exception e) {
			Logger.logMsg(Logger.ERROR, e.getMessage());
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template_type/{id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getAll(@PathVariable Integer id) {

		try {
			TemplateType templateType = this.dao.getById(id);
			if (templateType == null) {
				return new ApiResponse().send(HttpStatus.NOT_FOUND, Constants.TEMPLATE_TYPE_NOT_FOUND);
			}
			return new ApiResponse(templateType).send(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template_type", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> insert(@Valid @RequestBody TemplateType templateType, Errors errors) {

		if (errors.hasErrors()) {
			return new ApiResponse().send(HttpStatus.BAD_REQUEST);
		}
		
		try {
			TemplateType type = this.dao.insert(templateType);
			return new ApiResponse(type).send(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template_type", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse> update(@Valid @RequestBody TemplateType templateType, Errors errors) {

		if (errors.hasErrors() || templateType.getId() == null) {
			return new ApiResponse().send(HttpStatus.BAD_REQUEST);
		}
		
		TemplateType type = this.dao.getById(templateType.getId());
		if (type == null) {
			return new ApiResponse().send(HttpStatus.NOT_FOUND, Constants.TEMPLATE_TYPE_NOT_FOUND);
		}
		
		try {
			return new ApiResponse(this.dao.update(templateType)).send(HttpStatus.OK);
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template_type/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {

		try {
			TemplateType templateType = this.dao.getById(id);
			if (templateType == null) {
				return new ApiResponse().send(HttpStatus.NOT_FOUND, Constants.TEMPLATE_TYPE_NOT_FOUND);
			}
			this.dao.delete(id);
			return new ApiResponse(templateType).send(HttpStatus.OK);
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
}
