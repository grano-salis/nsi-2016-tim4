package com.reporttemplateengine.models;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Template implements java.io.Serializable {

	private static final long serialVersionUID = -4315413428887136849L;
	private Integer id;

	@NotNull
	@NotEmpty
	private String name;
	
	private TemplateDefinition templateDefinition;
	
	public Template() {}
	
	public Template(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	public Template(Integer id, String name, TemplateDefinition templateDefinition) {
		super();
		this.id = id;
		this.name = name;
		this.templateDefinition = templateDefinition;
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

	public TemplateDefinition getTemplateDefinition() {
		return templateDefinition;
	}

	public void setTemplateDefinition(TemplateDefinition templateDefinition) {
		this.templateDefinition = templateDefinition;
	}
}
