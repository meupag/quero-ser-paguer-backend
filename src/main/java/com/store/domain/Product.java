package com.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.store.message.ValidatorFieldMessage;
import com.store.util.MoneySerializer;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Data
@JsonDeserialize(builder = Product.ProductBuilder.class)
@Builder(builderClassName = "ProductBuilder", toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "produto")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = ValidatorFieldMessage.NameEmpty)
    @Column(name = "nome", length = 100)
    private String name;

    @DecimalMin("0.00")
    @JsonSerialize(using = MoneySerializer.class)
    @Column(name = "preco_sugerido", precision = 10, scale = 2)
    private BigDecimal suggestedPrice;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(targetEntity = OrderItem.class, mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_produto")
    private ProductStatus productStatus;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductBuilder {
    }

}