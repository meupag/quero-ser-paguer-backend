package com.pag.db.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.pag.modelo.Pedido;
import com.pag.modelo.PedidoId;

@EnableScan
public interface PedidoRepository extends CrudRepository<Pedido,PedidoId>{



	
}
