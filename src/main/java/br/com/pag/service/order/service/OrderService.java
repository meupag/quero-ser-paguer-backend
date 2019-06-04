package br.com.pag.service.order.service;

import br.com.pag.service.order.exception.ClientNotFoundByIdConstraintException;
import br.com.pag.service.order.exception.ClientNotFoundByIdException;
import br.com.pag.service.order.exception.OrderItemNotFoundByIdException;
import br.com.pag.service.order.exception.OrderNotFoundByIdException;
import br.com.pag.service.order.exception.ProductNotFoundByIdConstraintException;
import br.com.pag.service.order.exception.ProductNotFoundByIdException;
import br.com.pag.service.order.model.ItemPedido;
import br.com.pag.service.order.model.Pedido;
import br.com.pag.service.order.model.Produto;
import br.com.pag.service.order.repository.ClientRepository;
import br.com.pag.service.order.repository.OrderItemRepository;
import br.com.pag.service.order.repository.OrderRepository;
import br.com.pag.service.order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = { "order, orders" })
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @CacheEvict(value ="orders", allEntries = true)
    @Transactional
    public Pedido create(final Pedido order) {
        validateConstraints(order);
        final Pedido createdOrder = orderRepository.save(order);

        order.getItensPedido().stream()
            .forEach(oi -> {
                oi.setIdPedido(createdOrder.getId());
                orderItemRepository.save(oi);
            });
        return order;
    }

    @Cacheable("orders")
    public List<Pedido> findAll() {
        return orderRepository.findAll();
    }

    @Cacheable(value ="order", key = "#orderId", unless="#result == null")
    public Optional<Pedido> findById(final Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Caching(evict = {
        @CacheEvict(value ="orders", allEntries = true),
        @CacheEvict(value ="order", key = "#orderId", beforeInvocation = true)
    })
    public Pedido delete(final Long orderId) {
        return orderRepository.findById(orderId)
            .map(o -> {
                orderRepository.delete(o);
                return o;
            })
            .orElseThrow(() -> new OrderNotFoundByIdException(orderId));
    }

    @Caching(evict = {
        @CacheEvict(value ="orders", allEntries = true),
        @CacheEvict(value ="order", key = "#orderId", beforeInvocation = true)
    })
    public ItemPedido deleteOrderItem(final Long orderId, final Long orderItemId) {
        return orderItemRepository.findByIdAndIdPedido(orderItemId, orderId)
            .map(oi -> {
                orderItemRepository.delete(oi);
                return oi;
            })
            .orElseThrow(() -> new OrderItemNotFoundByIdException(orderItemId, orderId));
    }

    @CachePut(value="order", key = "#order.id")
    @CacheEvict(value ="orders", allEntries = true)
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

    private void validateConstraints(Pedido order) {
        clientRepository.findById(order.getIdCliente())
            .orElseThrow(() -> new ClientNotFoundByIdConstraintException(order.getIdCliente()));

        order.getItensPedido().stream()
            .map(ItemPedido::getIdProduto)
            .forEach(pId -> {
                productRepository.findById(pId)
                    .orElseThrow(() -> new ProductNotFoundByIdConstraintException(pId));
            });
    }
}
