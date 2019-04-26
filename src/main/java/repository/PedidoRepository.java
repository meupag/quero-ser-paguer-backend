package main.java.repository;

import java.util.List;
import main.java.models.Pedido;
import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {
}