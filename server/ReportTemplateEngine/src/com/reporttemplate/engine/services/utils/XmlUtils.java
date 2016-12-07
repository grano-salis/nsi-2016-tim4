package com.reporttemplate.engine.services.utils;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML Utilities.
 */
public class XmlUtils {

	/**
	 * Gets all nodes under a specified element, that are instances of a specified
	 * class.
	 * 
	 * @param <T>
	 * @param element
	 *        Element under which the nodes are checked.
	 * @param clazz
	 *        All elements are checked to be instances of this class.
	 * @param nodes
	 *        Nodes matching the criteria are added to this list.
	 *        The list is not cleared in the beginning of the method.
	 */
	static <T> void getAllNodesOfType(Node element, Class<T> clazz, List<T> nodes) {
		if (clazz.isInstance(element)) {
			@SuppressWarnings("unchecked") T t = (T) element;
			nodes.add(t);
		}
		NodeList children = element.getChildNodes();
		int childrenCount = children.getLength();
		for (int i = 0; i < childrenCount; i++) {
			Node node = children.item(i);
			getAllNodesOfType(node, clazz, nodes);			
		}
	}


}