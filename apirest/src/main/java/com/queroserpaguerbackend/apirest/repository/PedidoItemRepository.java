package com.queroserpaguerbackend.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.queroserpaguerbackend.apirest.models.PedidoItem;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
	PedidoItem findById(long id);
}