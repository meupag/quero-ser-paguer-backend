package com.javatechie.spring.mysql.api.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javatechie.spring.mysql.api.model.ItemPedido;
import com.javatechie.spring.mysql.api.model.Pedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
	
	public List<ItemPedido> findByPedidoId(Long pedidoId);
	
	
	@Query("SELECT i FROM ItemPedido i WHERE i.idPedido = :idPedido AND i.idProduto = :idProduto")
	public ItemPedido findByPedidoProduto(@Param("idPedido") Long idPedido, @Param("idProduto") Long idProduto);
	
}

