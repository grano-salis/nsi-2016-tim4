package com.reporttemplate.engine.services.utils;


import java.util.ArrayList;
import java.util.List;

import org.odftoolkit.odfdom.dom.element.text.TextSpanElement;

/**
 * Span fragment that consists of a text.
 * 
 * The text could contain phrases separated by sequences of
 * more than one spaces.
 */
public class TextSpanFragment implements SpanFragment {
	/**
	 * Text.
	 */
	String text;
	
	/**
	 * Fragments.
	 */
	List<SpanFragment> fragments;

	/**
	 * Creates a new TextStringElement object. 
	 *
	 * @param text
	 */
	public TextSpanFragment(String text) {
		super();
		this.text = text;
		this.fragments = new ArrayList<SpanFragment>();
		analyze(text);
	}
	
	public void appendTo(TextSpanElement span) {
		for (SpanFragment fragment : fragments) {
			fragment.appendTo(span);
		}
	}
	
	
	/**
	 * Analyzes the specified string.
	 * 
	 * @param string
	 */
	void analyze(String string) {
		if (string==null) {
			return;
		}
		int indexOfFirstSpaceSeq = string.indexOf("  "); //$NON-NLS-1$
		if (indexOfFirstSpaceSeq<0) {
			PhraseSpanFragment phrase = new PhraseSpanFragment(string);			
			fragments.add(phrase);
			return;
		} else {
			String first = string.substring(0,indexOfFirstSpaceSeq);
			PhraseSpanFragment phrase = new PhraseSpanFragment(first);
			fragments.add(phrase);
		}
		int indexOfNonSpaceAfterSpaceSeq = 
			positionOfFirstNonSpace(string, indexOfFirstSpaceSeq);
		int spacesLength = indexOfNonSpaceAfterSpaceSeq - indexOfFirstSpaceSeq;
		SpaceSpanFragment space = new SpaceSpanFragment(spacesLength);
		fragments.add(space);
		if (indexOfNonSpaceAfterSpaceSeq<string.length()) {
			String substr = string.substring(indexOfNonSpaceAfterSpaceSeq);
			analyze(substr);
		}
	}
	
	/**
	 * Finds the position of the first non space character in the
	 * specified string, after the specified position.
	 *  
	 * @param string
	 *        String being processed.	           
	 * @param after
	 *        After this position starts the search for a non space
	 *        character in string.
	 *         
	 * @return Returns the place of first occurrence of a non space
	 *         character after the specified position.
	 */
	static int positionOfFirstNonSpace(String string, int after) {
		char[] chars = string.toCharArray();
		if (after>chars.length) {
			return chars.length;
		}
		for (int i = after; i < chars.length; i++) {
			if (chars[i]!=' ') {
				return i;
			}
		}		
		return string.length();
	}
	
}