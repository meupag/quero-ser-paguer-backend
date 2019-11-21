package com.store.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.store.message.ValidatorFieldMessage;
import com.store.repository.listener.OrderValueListener;
import com.store.util.MoneySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@JsonDeserialize(builder = OrderItem.OrderItemBuilder.class)
@Builder(builderClassName = "OrderItemBuilder", toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "item_pedido")
@EntityListeners(OrderValueListener.class)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonBackReference(value = "order")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto", nullable = false)
    private Product product;

    @DecimalMin("0.00")
    @NotNull(message = ValidatorFieldMessage.AmountEmpty)
    @JsonSerialize(using = MoneySerializer.class)
    @Column(name = "quantidade", precision = 10, scale = 2)
    private BigDecimal amount;

    @DecimalMin("0.00")
    @NotNull(message = ValidatorFieldMessage.PriceEmpty)
    @JsonSerialize(using = MoneySerializer.class)
    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal price;

    @JsonPOJOBuilder(withPrefix = "")
    public static class OrderItemBuilder {
    }

}
