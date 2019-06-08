package com.pag.db.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.pag.modelo.Produto;

@EnableScan
public interface ProdutoRepository extends CrudRepository<Produto,String>{



	
}
