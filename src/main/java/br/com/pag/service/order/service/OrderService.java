package br.com.pag.service.order.service;

import br.com.pag.service.order.exception.OrderItemNotFoundByIdException;
import br.com.pag.service.order.exception.OrderNotFoundByIdException;
import br.com.pag.service.order.model.ItemPedido;
import br.com.pag.service.order.model.Pedido;
import br.com.pag.service.order.repository.OrderItemRepository;
import br.com.pag.service.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public Pedido create(final Pedido order) {
        final Pedido createdOrder = orderRepository.save(order);

        order.getItensPedido().stream()
            .forEach(oi -> {
                oi.setIdPedido(createdOrder.getId());
                orderItemRepository.save(oi);
            });
        return order;
    }

    public List<Pedido> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Pedido> findById(final Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Pedido delete(final Long orderId) {
        return orderRepository.findById(orderId)
            .map(o -> {
                orderRepository.delete(o);
                return o;
            })
            .orElseThrow(() -> new OrderNotFoundByIdException(orderId));
    }

    public ItemPedido deleteOrderItem(final Long orderId, final Long orderItemId) {
        return orderItemRepository.findByIdAndIdPedido(orderItemId, orderId)
            .map(oi -> {
                orderItemRepository.delete(oi);
                return oi;
            })
            .orElseThrow(() -> new OrderItemNotFoundByIdException(orderItemId, orderId));
    }

    public Pedido update(Pedido order) {
        final Pedido savedOrder = orderRepository.findById(order.getId())
            .orElseThrow(() -> new OrderNotFoundByIdException(order.getId()));

        order.getItensPedido()
            .forEach(ip -> {
                savedOrder.getItensPedido().stream()
                    .filter(ips -> ips.getId().equals(ip.getId()))
                    .findAny()
                    .ifPresent(ips -> {
                        ips.modifyPropertiesTo(ip);
                        orderItemRepository.save(ips);
                    });
            });

        savedOrder.afterItemsSet();
        orderRepository.save(savedOrder);

        return savedOrder;
    }
}
