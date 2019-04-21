package main.java.repository;

import java.util.List;
import main.java.models.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

    List<Cliente> findByNome(String nome);
}