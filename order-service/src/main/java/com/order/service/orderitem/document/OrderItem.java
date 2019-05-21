package com.order.service.orderitem.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder
@Getter
@Document(value = "OrderItem")
@TypeAlias(value = "OrderItem")
public class OrderItem {

    @Id
    private String id;
    private String orderId;
    private String productId;
    private Integer quantity;
    private BigDecimal price;

    public void setId(String id) {
        this.id = id;
    }
}