package com.store.service;

import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.Product;
import com.store.exception.ResourceNotFoundException;
import com.store.message.ExceptionMessage;
import com.store.repository.OrderItemRepository;
import com.store.util.PropertiesCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final ProductService productService;
    private final PropertiesCopier copier;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, OrderService orderService, ProductService productService, PropertiesCopier copier) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.productService = productService;
        this.copier = copier;
    }

    public OrderItem save(Integer customerId, Integer orderId, OrderItem orderItem) {
        Order order = orderService.findOrder(customerId, orderId);
        Product product = orderItem.getProduct() != null ? productService.findProduct(orderItem.getProduct().getId()) : null;
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        return orderItemRepository.save(orderItem);
    }

    public OrderItem findOrderItem(Integer customerId, Integer orderId, Integer orderItemId) {
        return findOrderItemById(customerId, orderId, orderItemId);
    }


    public Page<OrderItem> findOrderItemByExample(Integer customerId, Integer orderId, OrderItem orderItem, Pageable pageable) {
        orderItem.setOrder(Order.builder().id(orderId).customer(Customer.builder().id(customerId).build()).build());
        return orderItemRepository.findAll(Example.of(orderItem), pageable);
    }


    public OrderItem updateById(Integer customerId, Integer orderId, Integer orderItemId, OrderItem orderItem) {
        orderItem.setId(orderItemId);
        OrderItem persisted = findOrderItemById(customerId, orderId, orderItemId);
        copier.copyProperties(orderItem, persisted);
        return persisted;
    }


    public void deleteById(Integer customerId, Integer orderId, Integer orderItemId) {
        if (orderItemRepository.existsByIdAndOrder_IdAndOrder_Customer_Id(orderItemId, orderId, customerId)) {
            orderItemRepository.deleteById(orderItemId);
        } else {
            throw new ResourceNotFoundException(ExceptionMessage.OrderItemNotFound);
        }
    }

    private OrderItem findOrderItemById(Integer customerId, Integer orderId, Integer orderItemId) throws ResourceNotFoundException {
        return orderItemRepository.findByIdAndOrder_IdAndOrder_Customer_Id(orderItemId, orderId, customerId).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.OrderItemNotFound));
    }

}
