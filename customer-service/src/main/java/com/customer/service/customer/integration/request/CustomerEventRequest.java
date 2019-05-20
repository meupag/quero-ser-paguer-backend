package com.customer.service.customer.integration.request;

import com.customer.service.customer.document.Customer;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerEventRequest {

    private Customer customer;
    private CustomerEventType customerEventType;
}
