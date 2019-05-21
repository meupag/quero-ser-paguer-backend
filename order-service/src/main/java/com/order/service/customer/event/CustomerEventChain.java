package com.order.service.customer.event;

import com.order.service.customer.receiver.request.CustomerEventRequest;

public interface CustomerEventChain {

    void execute (CustomerEventRequest customerEventRequest);

    boolean canHandle(CustomerEventRequest customerEventRequest);
}
