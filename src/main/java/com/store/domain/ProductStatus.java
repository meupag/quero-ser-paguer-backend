package com.store.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductStatus {

    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String value;

    ProductStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ProductStatus fromValue(String text) {
        for (ProductStatus b : ProductStatus.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

}
