package com.store.util;

import com.store.AbstractTest;
import com.store.domain.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(value = PropertiesCopier.class)
public class PropertiesCopierTest extends AbstractTest {

    @Autowired
    PropertiesCopier copier;

    private Customer customer;

    @Before
    public void setup() {
        super.setup();
        customer = setupCustomer();
    }

    @Test
    public void getCopierCustomerShouldReturnNotEquals() {
        Customer newCustomer = Customer.builder().build();
        Assert.assertNotEquals(customer, newCustomer);
    }

    @Test
    public void getCopierCustomerShouldReturnEquals() {
        Customer newCustomer = Customer.builder().build();
        copier.copyProperties(customer, newCustomer);
        Assert.assertEquals(customer, newCustomer);
    }

}
