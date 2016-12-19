package com.reporttemplateengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.reporttemplate.engine.services.utils.ApplicationContextProvider;
import com.reporttemplateengine.models.Placeholder;
import com.reporttemplateengine.models.ValidationRule;
import com.reporttemplateengine.models.mappers.ValidationRuleMapper;

public class ValidationRuleDAO extends BaseDAO implements ICrud<ValidationRule>, ApplicationContextAware {
	
	public ValidationRuleDAO() {
		
	}
	
	ApplicationContext applicationContext = null;

 
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        System.out.println("setting context");
        this.applicationContext = applicationContext;
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
                  ps.setString(2,  entity.getValue());
                  return ps;
              }
          },
          keyHolder);
	      System.out.println(keyHolder.getKey().toString());
	      entity.setId(keyHolder.getKey().intValue());
	      return entity;
	}

	public Map<String, Object> insertForPlaceholder(Integer placeholderId, Integer validationRuleId) {

	      KeyHolder keyHolder = new GeneratedKeyHolder();
	      
	      if (this.jdbcTemplate == null) {
	    	  org.apache.commons.dbcp2.BasicDataSource ds = ( org.apache.commons.dbcp2.BasicDataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
	    	  this.setDataSource(ds);
	      }
	      
	      this.jdbcTemplate.update(new PreparedStatementCreator() {

	    	  @Override
              public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                  PreparedStatement ps =
                      connection.prepareStatement("INSERT INTO PLACEHOLDERVALIDATION(placeholder_id, validation_rule_id) VALUES(?, ?)", new String[] {"ID"});
                  ps.setInt(1, placeholderId);
                  ps.setInt(2,  validationRuleId);
                  return ps;
              }
          },
          keyHolder);
	      Map<String, Object> placeholderValidation = new HashMap<>();
	      placeholderValidation.put("id", keyHolder.getKey().intValue());
	      placeholderValidation.put("placeholder_id", placeholderId);
	      placeholderValidation.put("validation_rule_id", validationRuleId);
	      return placeholderValidation;
	}
	
	@Transactional
	public List<Map<String, Object>> insertMultipleForPlaceholders(List<Map<String, Object>> params) {
		
		List<Map<String, Object>> toReturn = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : params) {
			System.out.println(map);
			toReturn.add(this.insertForPlaceholder((Integer)map.get("placeholder_id"), (Integer)map.get("validation_rule_id")));
		}
		return toReturn;
	}
	
	public Map<String, Object> updateForPlaceholder(Integer placeholderId, Integer validationRuleId, Integer rowId) {

	      if (this.jdbcTemplate == null) {
	    	  org.apache.commons.dbcp2.BasicDataSource ds = ( org.apache.commons.dbcp2.BasicDataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
	    	  this.setDataSource(ds);
	      }
	      
	      this.jdbcTemplate.update("UPDATE PLACEHOLDERVALIDATION SET validation_rule_id = ? WHERE placeholder_id = ? AND id = ?", validationRuleId, placeholderId, rowId);
	      Map<String, Object> placeholderValidation = new HashMap<>();
	      placeholderValidation.put("row_id", rowId);
	      placeholderValidation.put("placeholder_id", placeholderId);
	      placeholderValidation.put("validation_rule_id", validationRuleId);
	      return placeholderValidation;
	}
	
	@Transactional
	public List<Map<String, Object>> updateMultipleForPlaceholders(List<Map<String, Object>> params) {
		
		List<Map<String, Object>> toReturn = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : params) {
			if (map.get("validation_rule_id") == null) {
				this.deletePlaceholderValidation((Integer) map.get("row_id"));
			} else {
				toReturn.add(this.updateForPlaceholder((Integer)map.get("placeholder_id"), (Integer)map.get("validation_rule_id"), (Integer)map.get("row_id")));
			}
		}
		return toReturn;
	}

	@Override
	public ValidationRule update(ValidationRule entity) {

		this.jdbcTemplate.update("UPDATE VALIDATIONRULE SET name = ?, value = ? WHERE id = ?", entity.getName(), entity.getValue(), entity.getId());
      	return entity;
	}

	@Override
	public Integer delete(Integer id) {

	    this.jdbcTemplate.update("DELETE FROM VALIDATIONRULE WHERE id = ?", id);
	    return id;
	}
	
	public Integer deletePlaceholderValidation(Integer id) {

		if (this.jdbcTemplate == null) {
	    	  org.apache.commons.dbcp2.BasicDataSource ds = ( org.apache.commons.dbcp2.BasicDataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
	    	  this.setDataSource(ds);
	       }
	    this.jdbcTemplate.update("DELETE FROM PLACEHOLDERVALIDATION WHERE id = ?", id);
	    return id;
	} 
	
	public boolean deletePlaceholderValidation(Integer placeholderId, Integer ruleId) {

		if (this.jdbcTemplate == null) {
    	  org.apache.commons.dbcp2.BasicDataSource ds = ( org.apache.commons.dbcp2.BasicDataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
    	  this.setDataSource(ds);
       }
	    this.jdbcTemplate.update("DELETE FROM PLACEHOLDERVALIDATION WHERE placeholder_id = ? AND validation_rule_id = ?", placeholderId, ruleId);
	    return true;
	} 
	
	@Transactional
	public List<Integer> deletePlaceholderValidations(List<Integer> ids) {

		for (Integer id : ids) {
			this.deletePlaceholderValidation(id);
		}
	    return ids;
	}  

	@Transactional
	public List<Map<String, Object>> getValidationRulesForPlaceholders(Integer definitionId) {
		
		if (this.jdbcTemplate == null) {
    	  org.apache.commons.dbcp2.BasicDataSource ds = ( org.apache.commons.dbcp2.BasicDataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
    	  this.setDataSource(ds);
       }

		try {
			List<Map<String, Object>> maps = this.jdbcTemplate.queryForList("SELECT a.placeholder_id placeholderId, b.name name, b.id id, b.value value, a.id row_id from PLACEHOLDERVALIDATION a, VALIDATIONRULE b WHERE a.placeholder_id IN (SELECT id FROM PLACEHOLDER WHERE template_definition_id = ?) AND a.validation_rule_id = b.id",
																		    new Object[]{definitionId});
			return maps;
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<>();
		}
	}
}
