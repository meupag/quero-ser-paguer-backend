package com.order.service.order.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder
@Getter
@Document(value = "Order")
@TypeAlias(value = "Order")
public class Order {

    @Id
    private String id;
    private String customerId;
    private BigDecimal value;

    public void setId(String id) {
        this.id = id;
    }
}