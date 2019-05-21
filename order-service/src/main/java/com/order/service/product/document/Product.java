package com.order.service.product.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder
@Getter
@Document(value = "Product")
@TypeAlias(value = "Product")
public class Product {

    @Id
    private String id;
    private String name;
    private BigDecimal recommendationPrice;

    public void setId(String id) {
        this.id = id;
    }
}