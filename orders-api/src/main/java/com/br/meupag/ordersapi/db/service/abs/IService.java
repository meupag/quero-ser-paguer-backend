package com.br.meupag.ordersapi.db.service.abs;

import java.util.List;

import com.br.meupag.ordersapi.domain.abs.Domain;

public interface IService<E extends Domain> {
	public E createNew(E entity) throws Exception;
	public E findOne(E entity) throws Exception;
	public E update(E newEntity) throws Exception;
	public void delete(E entity) throws Exception;
	public List<E> findAll() throws Exception;
}
