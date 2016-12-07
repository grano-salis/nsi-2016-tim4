package com.reporttemplate.engine.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.converter.core.ODFConverterException;
import org.odftoolkit.odfdom.converter.pdf.PdfConverter;
import org.odftoolkit.odfdom.converter.pdf.PdfOptions;
import org.odftoolkit.odfdom.dom.element.text.TextLineBreakElement;
import org.odftoolkit.odfdom.dom.element.text.TextSpanElement;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldDeclElement;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldGetElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.w3c.dom.NodeList;

import com.reporttemplate.engine.services.utils.OdfUtils;
import com.reporttemplate.engine.services.utils.TextSpanFragment;

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
			  if (element.getOfficeStringValueAttribute() != null) {
				  fields.add(element.getTextNameAttribute());  
			  }
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return fields;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean fillPlaceholders(String filePath, String outFilePath, Map<String, Object> placeholders) {
		
		try {

			Map<String, Object> placeholderMap = (Map<String, Object>) placeholders.get("placeholders");
			File file = new File(filePath);
			OdfDocument document = OdfDocument.loadDocument(file);
			
			List<TextUserFieldGetElement> list = OdfUtils.getUserFieldGetElements(document.getContentDom());
			
			for (int i = 0; i < list.size(); i++) {

				TextUserFieldGetElement element = (TextUserFieldGetElement) list.get(i);
				if (placeholderMap.get(element.getTextNameAttribute()) != null) { 
					replaceField(document.getContentDom(), element, placeholderMap.get(element.getTextNameAttribute()).toString());
				} else {
					replaceField(document.getContentDom(), element, "");
				}
			}

			document.save(outFilePath);
			convertToPdf(document, outFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private static void convertToPdf(OdfDocument document, String outFilePath) {

	    OutputStream out = null;
		try {
			out = new FileOutputStream(outFilePath.replace(".odt", ".pdf"));
			PdfOptions options = PdfOptions.create().fontEncoding("windows-1250");
		    PdfConverter.getInstance().convert(document, out, options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ODFConverterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
	}
	
	private static void replaceField(OdfFileDom dom, TextUserFieldGetElement field, String value){
		TextSpanElement newTextSpan = new TextSpanElement(dom);
		String txt = value.trim();		
		String[] texts = txt.split("\\r\\n");		
		for (int i = 0; i < texts.length; i++) {
			String token = texts[i];			
			TextSpanFragment fragment = new TextSpanFragment(token);			
			fragment.appendTo(newTextSpan);				
			if (i != texts.length - 1) {
				TextLineBreakElement nl = new TextLineBreakElement(dom);
				newTextSpan.appendChild(nl);				
			}
		}
		OdfUtils.replace(newTextSpan, field);
	}
}
