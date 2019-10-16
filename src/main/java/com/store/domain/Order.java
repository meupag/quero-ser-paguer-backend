package com.store.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.store.util.MoneySerializer;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

@Data
@JsonDeserialize(builder = Order.OrderBuilder.class)
@Builder(builderClassName = "OrderBuilder", toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pedido")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonBackReference(value = "customer")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    @DecimalMin("0.00")
    @JsonSerialize(using = MoneySerializer.class)
    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal value;

    @ToString.Exclude
    @JsonManagedReference(value = "order")
    @OneToMany(targetEntity = OrderItem.class, mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItemList;

    @JsonPOJOBuilder(withPrefix = "")
    public static class OrderBuilder {
    }
}
