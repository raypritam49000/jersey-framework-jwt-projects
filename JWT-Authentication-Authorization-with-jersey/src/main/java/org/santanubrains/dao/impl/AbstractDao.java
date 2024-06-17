package org.santanubrains.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.santanubrains.dao.interfaces.Dao;
import org.santanubrains.hibernateUtil.HibernateUtil;

public class AbstractDao<T, ID extends Serializable> implements Dao<T, ID> {

	private Class<T> persistentClass;
	private Session session;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		super();
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.persistentClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public T findById(ID id) {
		return (T) this.getSession().get(this.getPersistentClass(), id);
	}

	@Override
	public List<T> findAll() {
		return this.findByCriteria();
	}
	
	@Override
	public T findByCondition() {
		return this.findByCriteriaCondition();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = this.getSession().createCriteria(this.getPersistentClass()).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		for (Criterion c : criterion) {
			crit.add(c);
		}
		System.out.println((List<T>) crit.list());
		return (List<T>) crit.list();
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T findByCriteriaCondition(Criterion... criterion) {
		Criteria crit = this.getSession().createCriteria(this.getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return (T) crit.uniqueResult();
	}

	@Override
	public T save(T entity) {
		this.getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public void delete(T entity) {
		this.getSession().delete(entity);

	}

	@Override
	public void flush() {
		this.getSession().flush();

	}

	@Override
	public void clear() {
		this.getSession().clear();

	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Override
	public void setSession(Session session) {
		this.session = session;

	}

	protected Session getSession() {
		if (this.session == null) {
			this.session = HibernateUtil.getSessionfactory().getCurrentSession();
		}
		return this.session;
	}


}
