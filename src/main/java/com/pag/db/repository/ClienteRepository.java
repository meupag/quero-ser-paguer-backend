package com.pag.db.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.pag.modelo.Cliente;

@EnableScan
public interface ClienteRepository extends CrudRepository<Cliente,String>{



	
}
