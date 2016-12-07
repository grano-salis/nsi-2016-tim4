package com.reporttemplate.engine.services.utils;

import org.odftoolkit.odfdom.dom.element.text.TextSpanElement;

/**
 * Element of a String.
 * 
 * Represents a text, or a sequence of blank spaces.
 */
public interface SpanFragment {
	
	
	/**
	 * Appends this string element to the specified TextSpanElement.
	 * 
	 * @param span
	 */
	void appendTo(TextSpanElement span);
}