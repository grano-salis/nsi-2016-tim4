package com.reporttemplateengine.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.reporttemplateengine.models.Template;


public class TemplateDAO implements ICrud<Template> {

	private SessionFactory sessionFactory;
	
	public TemplateDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Template> getAll() {

		return (List<Template>) sessionFactory.getCurrentSession()
											  .createCriteria(Template.class)
											  .list();
	}

	@Override
	@Transactional
	public Template getById(Integer id) {
		
		return (Template) sessionFactory.getCurrentSession()
										.get(Template.class, id);
	}

	@Override
	@Transactional
	public Template insert(Template entity) throws RuntimeException {

		Transaction tx = null;
		Session session = sessionFactory.openSession();
		try {

		  tx = session.beginTransaction();

		  Integer id = (Integer) session.save(entity);

		  tx.commit();
		  
		  entity.setId(id);
		  return entity;
		} catch (RuntimeException e) {
		  tx.rollback();
		  throw e;
		}
	}

	@Override
	@Transactional
	public Template update(Template entity) {
		
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		try {

		  tx = session.beginTransaction();
		  
		  session.update(entity);

		  tx.commit();

		  return entity;
		} catch (RuntimeException e) {
		  tx.rollback();
		  throw e;
		}
	}

	@Override
	@Transactional
	public Integer delete(Integer id) {
		
		
		Template dummy = new Template();
		dummy.setId(id);
		
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		try {

		  tx = session.beginTransaction();

		  session.delete(dummy);
		  tx.commit();

		  return id;
		} catch (RuntimeException e) {
		  tx.rollback();
		  throw e;
		}
	}

	
}
