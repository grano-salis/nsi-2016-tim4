package com.reporttemplateengine.models.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.reporttemplateengine.models.TemplateDefinition;

public class TemplateDefinitionIdMapper implements RowMapper<TemplateDefinition> {

	public TemplateDefinition mapRow(ResultSet rs, int rowNum) throws SQLException {
		  TemplateDefinition def = new TemplateDefinition();
		  def.setId(rs.getInt("id"));
	      return def;
	   }
}
