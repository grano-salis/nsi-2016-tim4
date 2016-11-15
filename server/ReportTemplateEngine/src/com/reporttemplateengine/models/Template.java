package com.reporttemplateengine.models;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Template implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4315413428887136849L;
	private Integer id;

	@NotNull
	@NotEmpty
	private String name;
	
	public Template() {}
	
	public Template(Integer id, String name) {

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
