package com.store.repository.listener;

import com.store.domain.OrderItem;
import com.store.service.OrderService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
public class OrderValueListener {

    private final OrderService orderService;

    public OrderValueListener(@Lazy OrderService orderService) {
        this.orderService = orderService;
    }

    @PostPersist
    @PostUpdate
    private void onPostPersist(OrderItem entity) {
        orderService.updateValueById(entity.getOrder().getCustomer().getId(), entity.getOrder().getId());
    }

}
