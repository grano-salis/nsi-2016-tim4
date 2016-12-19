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
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.converter.core.ODFConverterException;
import org.odftoolkit.odfdom.converter.pdf.PdfConverter;
import org.odftoolkit.odfdom.converter.pdf.PdfOptions;
import org.odftoolkit.odfdom.dom.element.text.TextLineBreakElement;
import org.odftoolkit.odfdom.dom.element.text.TextSpanElement;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldDeclElement;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldGetElement;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions.FontStyle;
import org.odftoolkit.simple.style.StyleTypeDefinitions.HorizontalAlignmentType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Column;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
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
			TextDocument document = TextDocument.loadDocument(file);
			
			List<TextUserFieldGetElement> list = OdfUtils.getUserFieldGetElements(document.getContentDom());
			
			for (int i = 0; i < list.size(); i++) {

				TextUserFieldGetElement element = (TextUserFieldGetElement) list.get(i);
				if (placeholderMap.get(element.getTextNameAttribute()) != null) {

					if (element.getTextNameAttribute().contains("_Table_")) {
						Map<String, Object> tableMap = (Map<String, Object>) placeholderMap.get(element.getTextNameAttribute());
						createTableWithData(document, (List<String>) tableMap.get("columns"), (List<Map<String, Object>>)tableMap.get("data"));;
						replaceField(document.getContentDom(), element, "");
					}
					else {
						replaceField(document.getContentDom(), element, placeholderMap.get(element.getTextNameAttribute()).toString());
					}
				} else {
					replaceField(document.getContentDom(), element, "");
				}
			}

			document.save(outFilePath);
			document.close();
			convertToPdf(outFilePath, outFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Table createTableWithData(TextDocument document, List<String> columns, List<Map<String, Object>> data) {
		Table table = document.addTable(data.size() + 1, columns.size());
		for (int i = 0; i < columns.size(); i++) {
			Column col = table.getColumnByIndex(i);
			col.setUseOptimalWidth(true);
			Cell cell = table.getCellByPosition(i, 0);
			cell.setDisplayText(columns.get(i).toUpperCase().substring(0, 1) + columns.get(i).toLowerCase().substring(1));
			cell.setHorizontalAlignment(HorizontalAlignmentType.CENTER);
			cell.setFont(new Font("Arial", FontStyle.ITALIC, 10));
		}
		// append rows
		for (int i = 0; i < data.size(); i++) {
			Row row = table.getRowByIndex(i + 1);
			// iterate through cells
			for (int j = 0; j < row.getCellCount(); j++) {
				Cell cell = row.getCellByIndex(j);
				cell.setHorizontalAlignment(HorizontalAlignmentType.CENTER);
				cell.setFont(new Font("Arial", FontStyle.ITALIC, 10));
				cell.setDisplayText((String) data.get(i).get(columns.get(j))); 
			}
		}
		return table;
	}
	
	private static void convertToPdf(String filePath, String outFilePath) {

	    OutputStream out = null;
	    OdfDocument document = null;
		try {
			document = OdfDocument.loadDocument(filePath);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			out = new FileOutputStream(outFilePath.replace(".odt", ".pdf"));
			PdfOptions options = PdfOptions.create();
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
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * Replaces an element with another element
	 * @param newElement
	 * @param oldElement
	 */
	private static void replace(OdfElement newElement, OdfElement oldElement) {
		OdfElement parent = (OdfElement) oldElement.getParentNode();
		parent.replaceChild(newElement, oldElement);
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
