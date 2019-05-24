package br.com.pag.service.order.repository;

import br.com.pag.service.order.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<ItemPedido, Long> {

    Optional<ItemPedido> findByIdAndIdPedido(Long id, Long idPedido);
}
