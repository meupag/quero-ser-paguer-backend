package com.javatechie.spring.mysql.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.javatechie.spring.mysql.api.model.Cliente;
import com.javatechie.spring.mysql.api.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	public Optional<Cliente> findByNome(String nome);
	
}
