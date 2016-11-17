package com.reporttemplateengine.models.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.reporttemplateengine.models.TemplateType;

public class TemplateTypeMapper implements RowMapper<TemplateType> {

   public TemplateType mapRow(ResultSet rs, int rowNum) throws SQLException {
	  TemplateType type = new TemplateType();
	  type.setId(rs.getInt("id"));
	  type.setName(rs.getString("name"));
      return type;
   }
}
