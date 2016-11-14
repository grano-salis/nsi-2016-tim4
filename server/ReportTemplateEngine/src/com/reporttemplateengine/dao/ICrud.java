package com.reporttemplateengine.dao;

import java.util.List;

public interface ICrud<T> {

	public List<T> getAll();
	public T getById(Integer id);
	public T insert(T entity);
	public T update(T entity);
	public Integer delete(Integer id);
}
