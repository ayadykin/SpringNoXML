package com.ayadykin.hibernate.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ayadykin.hibernate.GenericDao;

public class GenericDaoHibernate<T, PK extends Serializable> implements
		GenericDao<T, PK> {
	//protected final Log log = LogFactory.getLog(getClass());

	protected final Class<T> persistentClass;
	@PersistenceContext
	private EntityManager entityManager;

	public GenericDaoHibernate(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Override
	public List<T> getAll() {
		return null;//this.getHibernateTemplate().loadAll(this.persistentClass);
	}

	@Override
	public T get(PK id) {
		T entity = entityManager.find(persistentClass, id);
		return entity;
	}

	@Override
	public boolean exists(PK id) {
		T entity = entityManager.find(persistentClass, id);
		return entity != null;
	}

	@Override
	public T save(T object) {
		entityManager.persist(object);
		return object;
	}

	@Override
	public void remove(PK id) {
		try {
			T obj = this.get(id);
			entityManager.remove(obj);
		} catch (Exception ex) {
			//log.error(ex.getMessage());
		}
	}
}
