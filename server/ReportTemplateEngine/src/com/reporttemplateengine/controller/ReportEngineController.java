package com.reporttemplateengine.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.jopendocument.dom.template.JavaScriptFileTemplate;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldDeclElement;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.NodeList;

import com.reporttemplate.engine.services.JOpenDocumentService;
import com.sun.media.jfxmedia.logging.Logger;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import sun.rmi.runtime.Log;

@Controller
public class ReportEngineController {

	
	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {
 
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
		return new ModelAndView("welcome", "message", message);
	}
	
	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadTemplate", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(@RequestParam("template") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("C:\\Users\\Granulo\\Desktop");
				File dir = new File(rootPath + "\\tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				String filePath = dir.getAbsolutePath()
						+ "\\" + file.getOriginalFilename();
				// Create the file on server
				File serverFile = new File(filePath);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				Map<String, Object> params = new HashMap<>();
				params.put("message", "You successfully uploaded file=" + file.getOriginalFilename());
				params.put("placeholders", JOpenDocumentService.getPlaceholders(serverFile));
				params.put("filePath", filePath);
				
				return new ModelAndView("index", "params", params);
			} catch (Exception e) {
				return new ModelAndView("index", "message", "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
			}
		} else {
			return new ModelAndView("index", "message", "You failed to upload " + file.getOriginalFilename() + " because the file was empty.");
		}
	}

	/**
	 * Generates report from placeholders and template
	 * @return
	 */
	@RequestMapping(value = "/generateReport", method = RequestMethod.POST)
	public ModelAndView generateReport(@RequestParam MultiValueMap<String, Object> parameters) {
		
		String filePath = parameters.get("filePath").get(0).toString();
		parameters.remove("filePath");
		if (JOpenDocumentService.fillPlaceholders(filePath, parameters)) {
			return new ModelAndView("index", "generated", "Successfully generated: " + filePath);
		} else {
			return new ModelAndView("index", "generated", "Generating report failed");
		}
		
	}
}
