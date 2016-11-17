package com.reporttemplateengine.models.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.reporttemplateengine.models.Placeholder;

public class PlaceholderMapper implements RowMapper<Placeholder> {

   public Placeholder mapRow(ResultSet rs, int rowNum) throws SQLException {
	  Placeholder placeholder = new Placeholder();
	  placeholder.setId(rs.getInt("id"));
	  placeholder.setName(rs.getString("name"));
	  placeholder.setDescription(rs.getString("description"));
	  placeholder.setType(rs.getString("type"));
	  placeholder.setTemplateDefinitionId(rs.getInt("template_definition_id"));
      return placeholder;
   }
}
