package com.queroserpaguerbackend.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.queroserpaguerbackend.apirest.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	Pedido findById(long id);
}