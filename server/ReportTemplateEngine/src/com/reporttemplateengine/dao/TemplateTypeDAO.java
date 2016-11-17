package com.reporttemplateengine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.reporttemplateengine.models.TemplateType;
import com.reporttemplateengine.models.mappers.TemplateTypeMapper;

public class TemplateTypeDAO extends BaseDAO implements ICrud<TemplateType> {

	public TemplateTypeDAO() {
		
	}

	@Override
	public List<TemplateType> getAll() {

		return this.jdbcTemplate.query("SELECT * FROM TEMPLATETYPE", new TemplateTypeMapper());
	}

	@Override
	public TemplateType getById(Integer id) {

		try {
			return this.jdbcTemplate.queryForObject("SELECT * FROM TEMPLATETYPE WHERE id = ?",
					new Object[]{id},
					new TemplateTypeMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public TemplateType insert(TemplateType entity) {

		  System.out.println(entity.getName());
	      KeyHolder keyHolder = new GeneratedKeyHolder();
	      this.jdbcTemplate.update(new PreparedStatementCreator() {

	    	  @Override
              public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                  PreparedStatement ps =
                      connection.prepareStatement("INSERT INTO TEMPLATETYPE(name) VALUES(?)", new String[] {"ID"});
                  ps.setString(1, entity.getName());
                  return ps;
              }
          },
          keyHolder);
	      System.out.println(keyHolder.getKey().toString());
	      entity.setId(keyHolder.getKey().intValue());
	      return entity;
	}

	@Override
	public TemplateType update(TemplateType entity) {

		this.jdbcTemplate.update("UPDATE TEMPLATETYPE SET name = ? WHERE id = ?", entity.getName(), entity.getId());
      	return entity;
	}

	@Override
	public Integer delete(Integer id) {

	    this.jdbcTemplate.update("DELETE FROM TEMPLATETYPE WHERE id = ?", id);
	    return id;
	}

	
}
