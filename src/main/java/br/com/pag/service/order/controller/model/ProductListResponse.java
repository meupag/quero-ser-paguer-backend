package br.com.pag.service.order.controller.model;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Getter
public class ProductListResponse extends ResourceSupport {

    private List<ProductResponse> productResponses;

    public ProductListResponse(List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }

    public int getSize() {
        if (productResponses == null) {
            return 0;
        }
        return productResponses.size();
    }
}
