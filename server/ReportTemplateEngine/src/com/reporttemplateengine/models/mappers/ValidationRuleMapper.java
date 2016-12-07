package com.reporttemplateengine.models.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.reporttemplateengine.models.ValidationRule;

public class ValidationRuleMapper implements RowMapper<ValidationRule> {

   public ValidationRule mapRow(ResultSet rs, int rowNum) throws SQLException {
	  ValidationRule type = new ValidationRule();
	  type.setId(rs.getInt("id"));
	  type.setName(rs.getString("name"));
	  type.setValue(rs.getString("value"));
      return type;
   }
}
