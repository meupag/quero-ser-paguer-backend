package main.java.repository;

import java.util.List;
import main.java.models.ItemPedido;
import org.springframework.data.repository.CrudRepository;

public interface ItemPedidoRepository extends CrudRepository<ItemPedido, Integer> {
}