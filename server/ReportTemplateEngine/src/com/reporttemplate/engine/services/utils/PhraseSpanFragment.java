package com.reporttemplate.engine.services.utils;

import org.odftoolkit.odfdom.dom.element.text.TextSpanElement;

/**
 * Span fragment that consists of a phrase.
 */
public class PhraseSpanFragment 
implements SpanFragment {
	/**
	 * Text.
	 */
	String text;

	/**
	 * Creates a new TextStringElement object. 
	 *
	 * @param text
	 */
	PhraseSpanFragment(String text) {
		super();
		this.text = text;
	}

	
	public void appendTo(TextSpanElement span) {
		span.newTextNode(text);		
	}

}
