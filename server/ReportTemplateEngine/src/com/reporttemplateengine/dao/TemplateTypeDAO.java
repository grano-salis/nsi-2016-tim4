package com.reporttemplateengine.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.reporttemplateengine.models.TemplateType;

public class TemplateTypeDAO implements ICrud<TemplateType> {

	private SessionFactory sessionFactory;
	
	public TemplateTypeDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<TemplateType> getAll() {

		return (List<TemplateType>) sessionFactory.getCurrentSession()
												  .createCriteria(TemplateType.class)
												  .list();
	}

	@Override
	@Transactional
	public TemplateType getById(Integer id) {
		
		return (TemplateType) sessionFactory.getCurrentSession()
											.get(TemplateType.class, id);
	}

	@Override
	@Transactional
	public TemplateType insert(TemplateType entity) throws RuntimeException {

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
	public TemplateType update(TemplateType entity) {
		
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
		
		
		TemplateType dummy = new TemplateType();
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
