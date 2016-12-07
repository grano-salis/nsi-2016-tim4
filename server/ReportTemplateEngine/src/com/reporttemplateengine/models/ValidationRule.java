package com.reporttemplateengine.models;

import javax.validation.constraints.NotNull;

public class ValidationRule {

	@NotNull
	private int id;
	private String name;
	private String value;
	
	public ValidationRule() {
		
	}

	public ValidationRule(int id, String name, String regex) {
		super();
		this.id = id;
		this.name = name;
		this.value = regex;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
