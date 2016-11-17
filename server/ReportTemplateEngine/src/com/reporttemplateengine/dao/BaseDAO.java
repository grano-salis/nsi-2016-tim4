package com.reporttemplateengine.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDAO {

	protected DataSource dataSource;
	protected JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      this.jdbcTemplate = new JdbcTemplate(dataSource);
   }
}
