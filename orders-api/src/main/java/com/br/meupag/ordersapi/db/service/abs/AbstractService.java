package com.br.meupag.ordersapi.db.service.abs;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;

import com.br.meupag.ordersapi.db.DbUtil;
import com.br.meupag.ordersapi.db.exception.EntityNotFoundException;
import com.br.meupag.ordersapi.domain.abs.Domain;

public abstract class AbstractService<E extends Domain> implements IService<E>  {

	private final SessionFactory sessionFactory;
	
	public AbstractService() {
		this.sessionFactory = DbUtil.getSessionFactory();
	}
	
	protected abstract void cleanRecursiveReferences(E entity) throws Exception;
	protected abstract void merge(E oldEntity, E newEntity) throws Exception;
	protected abstract String getFindAllQuery();
	
	public E createNew(E entity) throws Exception {
		EntityManager entityManager  = sessionFactory.createEntityManager();
		
		try {			
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
		} catch(Exception e) {
			entityManager.getTransaction().setRollbackOnly();
			throw e;
		} finally {
			if(entityManager.getTransaction().getRollbackOnly()) {
				entityManager.getTransaction().rollback();
			} else {
				entityManager.getTransaction().commit();
			}
			
			entityManager.close();
		}
		
		return this.findOne(entity);
	}
	
	@SuppressWarnings("unchecked")
	public E findOne(E entity) throws Exception {
		EntityManager entityManager = sessionFactory.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			entity = (E) entityManager.find(entity.getClass(), entity.getId());
		} catch(Exception e) {
			entityManager.getTransaction().setRollbackOnly();
			throw e;
		} finally {
			if(entityManager.getTransaction().getRollbackOnly()) {
				entityManager.getTransaction().rollback();
			} else {
				entityManager.getTransaction().commit();
			}
			
			entityManager.close();
		}
		
		if(entity == null) {
			throw new EntityNotFoundException();
		}
		
		this.cleanRecursiveReferences(entity);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public E update(E newEntity) throws Exception {		
		EntityManager entityManager  = sessionFactory.createEntityManager();
		E oldEntity = null;
		
		try {		
			entityManager.getTransaction().begin();
			oldEntity = (E) entityManager.find(newEntity.getClass(), newEntity.getId());
			
			if(oldEntity == null) {
				throw new EntityNotFoundException();
			}
			
			this.merge(oldEntity, newEntity);
			entityManager.persist(oldEntity);
		} catch(Exception e) {
			entityManager.getTransaction().setRollbackOnly();
			throw e;
		} finally {
			if(entityManager.getTransaction().getRollbackOnly()) {
				entityManager.getTransaction().rollback();
			} else {
				entityManager.getTransaction().commit();
			}
			
			entityManager.close();
		}
		
		this.cleanRecursiveReferences(oldEntity);
		return oldEntity;
	}	
	
	
	@SuppressWarnings("unchecked")
	public void delete(E entity) throws Exception {
		EntityManager entityManager = sessionFactory.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			entity = (E) entityManager.find(entity.getClass(), entity.getId());
			
			if(entity == null) {
				throw new EntityNotFoundException();
			}
			
			entityManager.remove(entity);
		} catch(Exception e) {
			entityManager.getTransaction().setRollbackOnly();
			throw e;
		} finally {
			if(entityManager.getTransaction().getRollbackOnly()) {
				entityManager.getTransaction().rollback();
			} else {
				entityManager.getTransaction().commit();
			}
			
			entityManager.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findAll() throws Exception {
		EntityManager entityManager = sessionFactory.createEntityManager();
		List<E> result = null;
		
		try {
			entityManager.getTransaction().begin();
			result = entityManager.createQuery(this.getFindAllQuery()).getResultList();
		} catch(Exception e) {
			entityManager.getTransaction().setRollbackOnly();
			throw e;
		} finally {
			if(entityManager.getTransaction().getRollbackOnly()) {
				entityManager.getTransaction().rollback();
			} else {
				entityManager.getTransaction().commit();
			}
			
			entityManager.close();
		}
		
		if(result == null || result.size() == 0) {
			throw new EntityNotFoundException();
		}
		
		for(E item : result) {
			this.cleanRecursiveReferences(item);
		}
		
		return result;
	}
	
}
