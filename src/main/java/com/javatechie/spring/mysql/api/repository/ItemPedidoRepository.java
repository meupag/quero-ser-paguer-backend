package com.javatechie.spring.mysql.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.javatechie.spring.mysql.api.model.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
	
	public List<ItemPedido> findByPedidoId(Long pedidoId);
	
	
	@Query("SELECT i FROM ItemPedido i WHERE i.idPedido = :idPedido AND i.idProduto = :idProduto")
	public ItemPedido findByPedidoProduto(@Param("idPedido") Long idPedido, @Param("idProduto") Long idProduto);
	
}

