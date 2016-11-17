package com.reporttemplateengine.controller;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reporttemplateengine.dao.ICrud;
import com.reporttemplateengine.helpers.ApiResponse;
import com.reporttemplateengine.helpers.Constants;
import com.reporttemplateengine.models.Template;
import com.reporttemplateengine.models.TemplateType;

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
			e.printStackTrace();
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
	public ResponseEntity<ApiResponse> insert(@RequestParam("template") String templateParam,
											  @RequestParam("file") MultipartFile file,
											  HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println(request.toString());
		System.out.println(request.getParameterNames().toString());
		Template template = (new ObjectMapper()).readValue(templateParam, Template.class);
		// convert to bytes here
		if (!file.isEmpty())
		{
			try {

				byte[] bytes = file.getBytes();
				template.getTemplateDefinition().setTemplateFile(bytes);
				Template type = this.dao.insert(template);
				return new ApiResponse(type).send(HttpStatus.OK);
			} catch (IOException e) {

				e.printStackTrace();
				return new ApiResponse().send(HttpStatus.BAD_REQUEST);
			} catch (Exception e) {

				e.printStackTrace();
				System.out.println(e.getMessage());
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
			}
		} else {
			return new ApiResponse().send(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse> update(@Valid @RequestBody Template templateType, Errors errors) {

		if (errors.hasErrors() || templateType.getId() == null) {
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
