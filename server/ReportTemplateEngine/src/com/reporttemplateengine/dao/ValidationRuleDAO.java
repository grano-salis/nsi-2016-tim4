package com.reporttemplateengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.reporttemplateengine.models.ValidationRule;
import com.reporttemplateengine.models.mappers.ValidationRuleMapper;

public class ValidationRuleDAO extends BaseDAO implements ICrud<ValidationRule> {

	public ValidationRuleDAO() {
		
	}

	@Override
	public List<ValidationRule> getAll() {

		return this.jdbcTemplate.query("SELECT * FROM VALIDATIONRULE", new ValidationRuleMapper());
	}

	@Override
	public ValidationRule getById(Integer id) {

		try {
			return this.jdbcTemplate.queryForObject("SELECT * FROM VALIDATIONRULE WHERE id = ?",
					new Object[]{id},
					new ValidationRuleMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public ValidationRule insert(ValidationRule entity) {

		  System.out.println(entity.getName());
	      KeyHolder keyHolder = new GeneratedKeyHolder();
	      this.jdbcTemplate.update(new PreparedStatementCreator() {

	    	  @Override
              public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                  PreparedStatement ps =
                      connection.prepareStatement("INSERT INTO VALIDATIONRULE(name, value) VALUES(?, ?)", new String[] {"ID"});
                  ps.setString(1, entity.getName());
                  ps.setString(2,  entity.getRegex());
                  return ps;
              }
          },
          keyHolder);
	      System.out.println(keyHolder.getKey().toString());
	      entity.setId(keyHolder.getKey().intValue());
	      return entity;
	}

	@Override
	public ValidationRule update(ValidationRule entity) {

		this.jdbcTemplate.update("UPDATE VALIDATIONRULE SET name = ?, value = ? WHERE id = ?", entity.getName(), entity.getRegex(), entity.getId());
      	return entity;
	}

	@Override
	public Integer delete(Integer id) {

	    this.jdbcTemplate.update("DELETE FROM VALIDATIONRULE WHERE id = ?", id);
	    return id;
	}

	
}
