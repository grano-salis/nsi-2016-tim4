package com.reporttemplate.engine.services.utils;

import org.odftoolkit.odfdom.dom.element.text.TextSElement;
import org.odftoolkit.odfdom.dom.element.text.TextSpanElement;

/**
 * Span fragment that consists of a sequence of spaces.
 */
public class SpaceSpanFragment 
implements SpanFragment {
	/**
	 * Length.
	 */
	int length;

	/**
	 * Creates a new TextStringElement object. 
	 *
	 * @param length
	 */
	SpaceSpanFragment(int length) {
		super();
		this.length = length;
	}

	
	public void appendTo(TextSpanElement span) {
		TextSElement space = span.newTextSElement();
		space.setTextCAttribute(length);
	}

}