package com.reporttemplateengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.reporttemplateengine.models.Placeholder;
import com.reporttemplateengine.models.mappers.PlaceholderMapper;

public class PlaceholderDAO extends BaseDAO implements ICrud<Placeholder> {

	public PlaceholderDAO() {
		
	}

	@Override
	public List<Placeholder> getAll() {

		return this.jdbcTemplate.query("SELECT * FROM PLACEHOLDER", new PlaceholderMapper());
	}

	public List<Placeholder> getAllForTemplate(Integer id) {

		try {
			return this.jdbcTemplate.query("SELECT * FROM PLACEHOLDER WHERE template_definition_id = ?",
					new Object[]{id},
					new PlaceholderMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Placeholder getById(Integer id) {

		try {
			return this.jdbcTemplate.queryForObject("SELECT * FROM PLACEHOLDER WHERE id = ?",
					new Object[]{id},
					new PlaceholderMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Placeholder insert(Placeholder entity) {

		  final String sql = "INSERT INTO PLACEHOLDER(name, description, type, template_definition_id) VALUES(?, ?, ?, ?)";
	      KeyHolder keyHolder = new GeneratedKeyHolder();
	      this.jdbcTemplate.update(new PreparedStatementCreator() {

	    	  @Override
              public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                  PreparedStatement ps =
                      connection.prepareStatement(sql, new String[] {"ID"});
                  ps.setString(1, entity.getName());
                  ps.setString(2, entity.getDescription());
                  ps.setString(3, entity.getType());
                  ps.setInt(4, entity.getTemplateDefinitionId());
                  return ps;
              }
          },
          keyHolder);
	      entity.setId(keyHolder.getKey().intValue());
	      return entity;
	}

	@Override
	public Placeholder update(Placeholder entity) {

		this.jdbcTemplate.update("UPDATE PLACEHOLDER SET name = ?, type = ?, template_definition_id = ?, description = ? WHERE id = ?",
								 entity.getName(), entity.getType(), entity.getTemplateDefinitionId(), entity.getDescription(), entity.getId());
      	return entity;
	}

	@Override
	public Integer delete(Integer id) {

	    this.jdbcTemplate.update("DELETE FROM PLACEHOLDER WHERE id = ?", id);
	    return id;
	}

	
}
