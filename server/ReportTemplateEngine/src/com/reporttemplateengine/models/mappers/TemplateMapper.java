package com.reporttemplateengine.models.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.reporttemplateengine.models.Template;
import com.reporttemplateengine.models.TemplateDefinition;
import com.reporttemplateengine.models.TemplateType;

public class TemplateMapper implements RowMapper<Template> {

   public Template mapRow(ResultSet rs, int rowNum) throws SQLException {

	  Template template = new Template();
	  
	  template.setId(rs.getInt("template_id"));
	  template.setName(rs.getString("template_name"));
	  
	  TemplateDefinition def = new TemplateDefinition();

	  def.setId(rs.getInt("template_definition_id"));
	  def.setTemplateFile(rs.getString("template_file"));
	  def.setActive(rs.getInt("active"));
	  def.setVersion(rs.getInt("version"));
	  
	  TemplateType type = new TemplateType();
	  type.setId(rs.getInt("template_type_id"));
	  type.setName(rs.getString("template_type_name"));
	  
	  def.setTemplateType(type);
	  template.setTemplateDefinition(def);
      return template;
   }
}
