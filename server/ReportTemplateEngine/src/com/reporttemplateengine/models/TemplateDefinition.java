package com.reporttemplateengine.models;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class TemplateDefinition {

	private int id;
	private TemplateType templateType;
	private byte[] templateFile;
	private int version;
	private int active;
	private List<Placeholder> placeholders;
	
	public TemplateDefinition() {}

	public TemplateDefinition(int id, TemplateType templateType, byte[] templateFile, int version,
			int active) {
		super();
		this.id = id;
		this.templateType = templateType;
		this.templateFile = templateFile;
		this.version = version;
		this.active = active;
	}

	public TemplateDefinition(int id, TemplateType templateType, byte[] templateFile, int version,
			int active, List<Placeholder> placeholders) {
		super();
		this.id = id;
		this.templateType = templateType;
		this.templateFile = templateFile;
		this.version = version;
		this.active = active;
		this.placeholders = placeholders;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}

	public byte[] getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(byte[] templateFile) {
		this.templateFile = templateFile;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public List<Placeholder> getPlaceholders() {
		return placeholders;
	}

	public void setPlaceholders(List<Placeholder> placeholders) {
		this.placeholders = placeholders;
	}
}
