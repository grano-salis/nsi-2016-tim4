package com.reporttemplate.engine.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.converter.pdf.PdfConverter;
import org.odftoolkit.odfdom.converter.pdf.PdfOptions;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldDeclElement;
import org.springframework.util.MultiValueMap;
import org.w3c.dom.NodeList;

public class JOpenDocumentService {

	public static LinkedList<String> getPlaceholders(File template) {

		LinkedList<String> fields = new LinkedList<>();

		// Parse placeholders from the file
		OdfDocument doc;
		try {
			doc = OdfDocument.loadDocument(template);
			NodeList nodes = doc.getContentDom().getElementsByTagName("text:user-field-decl");
			for (int i = 0; i < nodes.getLength(); i++) {

			  TextUserFieldDeclElement element = (TextUserFieldDeclElement) nodes.item(i);
			  fields.add(element.getTextNameAttribute());
			  System.out.println(element.getTextNameAttribute());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return fields;
	}
	
	public static boolean fillPlaceholders(String filePath, MultiValueMap<String, Object> placeholders) {
		
		try {

			File file = new File(filePath);
			OdfDocument document = OdfDocument.loadDocument(file);
			NodeList nodes = document.getContentDom().getElementsByTagName("text:user-field-decl");
			for (int i = 0; i < nodes.getLength(); i++) {

			  TextUserFieldDeclElement element = (TextUserFieldDeclElement) nodes.item(i);
			  element.setOfficeStringValueAttribute(placeholders.get(element.getTextNameAttribute()).get(0).toString());
			}
		//	PdfConverter converter = new PdfConverter();
		//	OutputStream out = new FileOutputStream(filePath);
			document.save(filePath);
		//	converter.convert(document.sa, out, PdfOptions.getDefault());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
