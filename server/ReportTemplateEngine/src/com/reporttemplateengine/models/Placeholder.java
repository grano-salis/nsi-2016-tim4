package com.reporttemplateengine.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public class Placeholder {

	private int id;
	private int templateDefinitionId;
	private String name;
	private String description;
	private String type;
	
	public Placeholder() {
		
	}

	public Placeholder(int id, int templateDefinitionId, String name, String description, String type) {
		super();
		this.id = id;
		this.templateDefinitionId = templateDefinitionId;
		this.name = name;
		this.description = description;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTemplateDefinitionId() {
		return templateDefinitionId;
	}

	public void setTemplateDefinitionId(int templateDefinitionId) {
		this.templateDefinitionId = templateDefinitionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
