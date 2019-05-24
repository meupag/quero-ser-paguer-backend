package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Produto;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ProductCreateRequest {

    @NotNull(message = "pag.product.required.name")
    @Size(max = 100, message = "pag.product.max.name")
    private String nome;

    @NotNull(message = "pag.product.required.precosugerido")
    @Digits(integer=10, fraction=2, message = "pag.product.invalid.precosugerido")
    private BigDecimal precoSugerido;

    public Produto toModel() {
        return new Produto()
            .setNome(nome)
            .setPrecoSugerido(precoSugerido);
    }
}
