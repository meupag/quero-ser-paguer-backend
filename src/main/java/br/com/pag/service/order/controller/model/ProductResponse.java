package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Produto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    private Long id;
    private String nome;
    private BigDecimal precoSugerido;

    public static ProductResponse fromModel(Produto product) {
        return new ProductResponse()
            .setId(product.getId())
            .setNome(product.getNome())
            .setPrecoSugerido(product.getPrecoSugerido());
    }
}
