package br.com.pag.service.order.repository;

import br.com.pag.service.order.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Cliente, Long> { }
