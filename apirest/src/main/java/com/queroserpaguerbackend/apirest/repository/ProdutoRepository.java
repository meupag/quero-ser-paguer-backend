package com.queroserpaguerbackend.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.queroserpaguerbackend.apirest.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	Produto findById(long id);
}
