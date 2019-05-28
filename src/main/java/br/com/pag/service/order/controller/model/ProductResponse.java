package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

@Data
public class ProductResponse extends ResourceSupport {

    @JsonProperty("id")
    private Long productId;
    private String nome;
    private BigDecimal precoSugerido;

    public static ProductResponse fromModel(Produto product) {
        return new ProductResponse()
            .setProductId(product.getId())
            .setNome(product.getNome())
            .setPrecoSugerido(product.getPrecoSugerido());
    }
}
