package com.reporttemplateengine.helpers;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Validator {

	public static boolean isRegexValid(String regex) {

        try {
            Pattern.compile(regex);
            return true;
        } catch (PatternSyntaxException exception) {
            System.err.println(exception.getDescription());
            return false;
        }
	}
}
