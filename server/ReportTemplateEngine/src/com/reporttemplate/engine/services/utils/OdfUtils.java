package com.reporttemplate.engine.services.utils;

import java.util.ArrayList;
import java.util.List;

import org.odftoolkit.odfdom.dom.element.text.TextUserFieldGetElement;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.w3c.dom.Node;

public class OdfUtils {

	public static void replace(OdfElement newElement, OdfElement oldElement) {
		OdfElement parent = (OdfElement) oldElement.getParentNode();
		System.out.println(parent.toString());
		System.out.println(newElement.toString());
		System.out.println(oldElement.toString());
		parent.replaceChild(newElement, oldElement);
	}
	
	/**
	 * Gets all TextUserFieldGetElement nodes of the specified dom.
	 * 
	 * @param dom
	 *        OdfFileDom to search for TextUserFieldGetElements.
	 *        
	 * @return Returns a list with all TextUserFieldGetElement of the dom.
	 */
	public static List<TextUserFieldGetElement> getUserFieldGetElements(OdfFileDom dom) {
		List<TextUserFieldGetElement> list = new ArrayList<TextUserFieldGetElement>();
		Node root = dom.getRootElement();
		XmlUtils.getAllNodesOfType(root, TextUserFieldGetElement.class, list);
		return list;
	}
}
