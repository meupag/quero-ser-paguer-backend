package main.java.repository;

import java.util.Set;
import main.java.models.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

    Cliente findById(int id);
    Set<Cliente> findAll();
}