package br.com.pag.service.order.repository;

import br.com.pag.service.order.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Produto, Long> { }
