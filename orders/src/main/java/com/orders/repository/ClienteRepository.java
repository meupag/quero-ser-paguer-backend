package com.orders.repository;

import java.util.Optional;

import com.orders.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByNomeAndCpf(String nome, String cpf);
}