package com.javatechie.spring.mysql.api.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.javatechie.spring.mysql.api.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	public List<Pedido> findByClienteCpf(String cpf);
	
	@Transactional
	@Modifying
	@Query("UPDATE Pedido SET valor = :valorAtual WHERE id = :idPedido")
	public void updateValor(@Param("valorAtual") BigDecimal valorAtual, @Param("idPedido") Long idPedido);
	
}
