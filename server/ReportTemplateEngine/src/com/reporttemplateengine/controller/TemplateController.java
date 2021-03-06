package com.reporttemplateengine.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.reporttemplate.engine.services.JOpenDocumentService;
import com.reporttemplateengine.dao.ICrud;
import com.reporttemplateengine.dao.ValidationRuleDAO;
import com.reporttemplateengine.helpers.ApiResponse;
import com.reporttemplateengine.helpers.Constants;
import com.reporttemplateengine.models.Placeholder;
import com.reporttemplateengine.models.Template;
import com.reporttemplateengine.models.TemplateType;

@RestController
@CrossOrigin
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
	
	@RequestMapping(value = "/api/services/data/template/{id}/placeholders", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getAllPlaceholders(@PathVariable Integer id) {

		try {
			Template templateType = this.dao.getById(id);
			if (templateType == null) {
				return new ApiResponse().send(HttpStatus.NOT_FOUND, Constants.TEMPLATE_NOT_FOUND);
			}
			return new ApiResponse(templateType.getTemplateDefinition().getPlaceholders()).send(HttpStatus.OK);
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template/{id}/placeholders/validationrules", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> insertValidationRules(@PathVariable Integer id,
															 @RequestBody Map<String, Object> placeholderValidation) {

		try {
			Template templateType = this.dao.getById(id);
			if (templateType == null) {
				return new ApiResponse().send(HttpStatus.NOT_FOUND, Constants.TEMPLATE_NOT_FOUND);
			}
			ValidationRuleDAO validationRuleDAO = new ValidationRuleDAO();
			return new ApiResponse(validationRuleDAO.insertMultipleForPlaceholders((List<Map<String, Object>>)placeholderValidation.get("placeholderValidation"))).send(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}
	
	@RequestMapping(value = "/api/services/data/template/{id}/placeholders/validationrules", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse> updateValidationRules(@PathVariable Integer id,
															 @RequestBody Map<String, Object> placeholderValidation) {

		try {
			Template templateType = this.dao.getById(id);
			if (templateType == null) {
				return new ApiResponse().send(HttpStatus.NOT_FOUND, Constants.TEMPLATE_NOT_FOUND);
			}
			ValidationRuleDAO validationRuleDAO = new ValidationRuleDAO();
			validationRuleDAO.updateMultipleForPlaceholders((List<Map<String, Object>>)placeholderValidation.get("placeholderValidation"));
			return new ApiResponse(this.dao.getById(id)).send(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}

	@RequestMapping(value = "/api/services/data/template/{templateId}/placeholders/validationrules", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> deleteValidationRules(@PathVariable Integer templateId,
															 @RequestBody Map<String, Object> ids) {

		try {
			Template templateType = this.dao.getById(templateId);
			if (templateType == null) {
				return new ApiResponse().send(HttpStatus.NOT_FOUND, Constants.TEMPLATE_NOT_FOUND);
			}
			ValidationRuleDAO validationRuleDAO = new ValidationRuleDAO();
			return new ApiResponse(validationRuleDAO.deletePlaceholderValidations((List<Integer>)ids.get("ids"))).send(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
	}

	@RequestMapping(value = "/api/services/data/template", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> insert(@RequestParam("template") String templateParam,
											  @RequestParam("file") MultipartFile file,
											  HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {

		Template template = (new ObjectMapper()).readValue(templateParam, Template.class);
		// convert to bytes here
		if (!file.isEmpty())
		{
			
			if (!FilenameUtils.getExtension(file.getOriginalFilename()).equals("odt")) {
				return new ApiResponse().send(HttpStatus.BAD_REQUEST, Constants.NOT_ODT_FILE);
			}
			try {

				 	String fileName = file.getOriginalFilename();
				 	(new File("files")).mkdirs();
				    File serverFile = new File("files" + File.separator + java.util.UUID.randomUUID() + ".odt");

				    OutputStream outputStream = null;
				    InputStream inputStream = null;
				    try {
				        inputStream = file.getInputStream();

				        if (!serverFile.exists()) {
				        	serverFile.createNewFile();
				        }
				        outputStream = new FileOutputStream(serverFile);
				        int read = 0;
				        byte[] bytes = new byte[1024];

				        while ((read = inputStream.read(bytes)) != -1) {
				            outputStream.write(bytes, 0, read);
				        }
				        template.getTemplateDefinition().setTemplateFile(serverFile.getAbsolutePath());
				        
				        // get placeholders
				        LinkedList<String> placeholders = JOpenDocumentService.getPlaceholders(serverFile);
				        LinkedList<Placeholder> holders = new LinkedList<>();
				        for(String placeholder : placeholders) {
				        	Placeholder p = new Placeholder();
				        	p.setName(placeholder);
				        	holders.add(p);
				        }
				        template.getTemplateDefinition().setPlaceholders(holders);
				        
						Template type = this.dao.insert(template);
						return new ApiResponse(type).send(HttpStatus.OK);
				    } catch (IOException e) {
				        e.printStackTrace();
				        return new ApiResponse().send(HttpStatus.BAD_REQUEST);
				    } finally {
				    	if (inputStream != null) {
				    		inputStream.close();
				    	}
				    	if (outputStream != null) {
				    		outputStream.close();
				    	}
				    }
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
	
	/**
	 * Takes id of template and returns generated .pdf, .odt file is also
	 * generated and accessible on path for .pdf
	 * @param id
	 * @param placeholders
	 * @param request
	 * @param response
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/services/data/template/{id}/fill", method = RequestMethod.POST)
	public byte[] fillTemplate(@PathVariable int id,
							 @RequestBody Map<String, Object> placeholders,
							 HttpServletRequest request,
							 HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {

			try {
				Template template = this.dao.getById(id);
				InputStream is = new FileInputStream(template.getTemplateDefinition().getTemplateFile());
				(new File("files/conversions")).mkdirs();
				String outFilePath = "files" + File.separator + "conversions" + File.separator + java.util.UUID.randomUUID() + ".odt";
				if (JOpenDocumentService.fillPlaceholders(template.getTemplateDefinition().getTemplateFile(),
													      outFilePath,
														  placeholders)) {  
					// copy it to response's OutputStream
					response.setContentType("application/pdf");
			        return IOUtils.toByteArray((new FileInputStream(outFilePath.replace(".odt", ".pdf"))));
				}
			} catch (Exception e) {

				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			return null;
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
