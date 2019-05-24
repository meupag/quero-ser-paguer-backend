package br.com.pag.service.order.repository;

import br.com.pag.service.order.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Pedido, Long> {}
