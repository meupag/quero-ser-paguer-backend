package com.queroserpaguerbackend.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.queroserpaguerbackend.apirest.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	Cliente findById(long id);
}
