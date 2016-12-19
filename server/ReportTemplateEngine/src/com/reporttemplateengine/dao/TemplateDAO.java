package com.reporttemplateengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;

import com.reporttemplateengine.helpers.AbstractLobPreparedStatementCreator;
import com.reporttemplateengine.models.Placeholder;
import com.reporttemplateengine.models.Template;
import com.reporttemplateengine.models.TemplateDefinition;
import com.reporttemplateengine.models.ValidationRule;
import com.reporttemplateengine.models.mappers.TemplateDefinitionIdMapper;
import com.reporttemplateengine.models.mappers.TemplateMapper;

public class TemplateDAO extends BaseDAO implements ICrud<Template>, ApplicationContextAware {
	
	private PlaceholderDAO placeholderDAO;

	public void setPlaceholderDAO(PlaceholderDAO placeholderDAO) {
		this.placeholderDAO = placeholderDAO;
	}
	
	ApplicationContext applicationContext = null;

	 
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


	@Override
	public List<Template> getAll() {
		
		String query = "SELECT a.id as template_id, a.name as template_name, "
							+ "b.id as template_definition_id, b.version as version, b.active as active, b.template_file as template_file, b.template_type_id as template_type_id, "
							+ "c.name as template_type_name "
							+ "FROM TEMPLATE a, TEMPLATEDEFINITION b, TEMPLATETYPE c "
							+ "WHERE a.id = b.template_id AND b.template_type_id = c.id";
		List<Template> templates = this.jdbcTemplate.query(query, new TemplateMapper());
		
		for (Template template : templates) {

			TemplateDefinition def = template.getTemplateDefinition();
			if (def != null) {
				List<Placeholder> placeholders = this.placeholderDAO
													 .getAllForTemplate(template.getTemplateDefinition().getId());
				if (placeholders != null) {
					def.setPlaceholders(placeholders);
				}
				ValidationRuleDAO validationRuleDAO = new ValidationRuleDAO();
				def.setValidationRules(validationRuleDAO.getValidationRulesForPlaceholders(def.getId()));
				template.setTemplateDefinition(def);
			}
		}
		return templates;
	}

	@Override
	public Template getById(Integer id) {
		
		try {
			String query = "SELECT a.id as template_id, a.name as template_name, "
					+ "b.id as template_definition_id, b.version as version, b.active as active, b.template_file as template_file, b.template_type_id as template_type_id, "
					+ "c.name as template_type_name "
					+ "FROM TEMPLATE a, TEMPLATEDEFINITION b, TEMPLATETYPE c "
					+ "WHERE a.id = b.template_id AND b.template_type_id = c.id AND a.id = ?";
			Template template = this.jdbcTemplate.queryForObject(query, new Object[] {id}, new TemplateMapper());

			TemplateDefinition def = template.getTemplateDefinition();
			if (def != null) {
				List<Placeholder> placeholders = this.placeholderDAO.getAllForTemplate(template.getTemplateDefinition().getId());
				if (placeholders != null) {
					def.setPlaceholders(this.placeholderDAO.getAllForTemplate(template.getTemplateDefinition().getId()));
				}
				ValidationRuleDAO validationRuleDAO = new ValidationRuleDAO();
				def.setValidationRules(validationRuleDAO.getValidationRulesForPlaceholders(def.getId()));
				template.setTemplateDefinition(def);
			}
			return template;
		} catch (EmptyResultDataAccessException e) { 
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Template insert(Template entity) {
		
		KeyHolder templateKeyHolder = new GeneratedKeyHolder();
	      this.jdbcTemplate.update(new PreparedStatementCreator() {

	    	  @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =
                    connection.prepareStatement("INSERT INTO TEMPLATE(name) VALUES(?)", new String[] {"ID"});
                ps.setString(1, entity.getName());
                return ps;
            }
        }, templateKeyHolder);
        entity.setId(templateKeyHolder.getKey().intValue());
      System.out.println(entity.getId());
      String definitionSql = "INSERT INTO TEMPLATEDEFINITION(template_id, template_type_id, template_file) VALUES(?, ?, ?)";

      KeyHolder definitionKeyHolder = new GeneratedKeyHolder();
      
      this.jdbcTemplate.update(new PreparedStatementCreator() {

    	  @Override
          public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
              PreparedStatement ps =
                  connection.prepareStatement(definitionSql, new String[] {"ID"});
              ps.setInt(1, entity.getId());
              ps.setInt(2, entity.getTemplateDefinition().getTemplateType().getId());
              ps.setString(3, entity.getTemplateDefinition().getTemplateFile());
              return ps;
          }
      }, definitionKeyHolder);

      entity.getTemplateDefinition().setId(definitionKeyHolder.getKey().intValue());
      /* Insert into placeholders table */
      for (Placeholder placeholder : entity.getTemplateDefinition().getPlaceholders()) {
    	  placeholder.setTemplateDefinitionId(entity.getTemplateDefinition().getId());
    	  Placeholder inserted = this.placeholderDAO.insert(placeholder);
    	  placeholder = inserted;
      }
      return entity;
	}

	@Override
	public Template update(Template entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		String definitionSql = "SELECT id FROM TEMPLATEDEFINITION WHERE template_id = ?";
		TemplateDefinition def = this.jdbcTemplate.queryForObject(definitionSql, new Object[] {id} ,new TemplateDefinitionIdMapper());
		this.jdbcTemplate.update("DELETE FROM PLACEHOLDER WHERE template_definition_id = ?", def.getId());
		this.jdbcTemplate.update("DELETE FROM TEMPLATEDEFINITION WHERE template_id = ?", id);
		this.jdbcTemplate.update("DELETE FROM TEMPLATE WHERE id = ?", id);
		return id;
	}

}
