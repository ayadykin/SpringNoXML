package com.ayadykin.hibernate.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(persistentClass);
        Root<T> rootEntry = cq.from(persistentClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
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
