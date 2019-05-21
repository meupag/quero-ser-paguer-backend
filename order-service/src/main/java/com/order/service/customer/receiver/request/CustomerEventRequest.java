package com.order.service.customer.receiver.request;

import com.order.service.customer.document.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEventRequest {

    private Customer customer;
    private CustomerEventType customerEventType;
}
