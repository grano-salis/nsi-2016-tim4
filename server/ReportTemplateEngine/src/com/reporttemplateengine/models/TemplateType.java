package com.reporttemplateengine.models;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class TemplateType implements java.io.Serializable {

	private static final long serialVersionUID = -4315413428887136849L;
	private Integer id;

	@NotNull
	@NotEmpty
	private String name;
	
	public TemplateType() {}

	public TemplateType(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
