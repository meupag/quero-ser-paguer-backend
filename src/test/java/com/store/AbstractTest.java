package com.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.Product;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Ignore
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AbstractTest {

    protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected static final Integer customerId = 1;
    protected static final Integer orderId = 1;
    protected static final Integer orderItemId = 1;
    protected static final Integer productId = 1;
    protected static final String basePath = "http://localhost";

    protected static final String customerPath = basePath + "/customers";
    protected static final String orderPath = customerPath + "/" + customerId + "/orders";
    protected static final String orderItemPath = orderPath + "/" + orderId + "/order-items";
    protected static final String productPath = basePath + "/products";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    private ApplicationContext applicationContext;

    @Before
    public void setup() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    protected Customer setupCustomer() {
        return (Customer) applicationContext.getBean("customer");
    }

    protected Order setupOrder() {
        return (Order) applicationContext.getBean("order");
    }

    protected OrderItem setupOrderItem() {
        return (OrderItem) applicationContext.getBean("orderItem");
    }

    protected Product setupProduct() {
        return (Product) applicationContext.getBean("product");
    }

    private <T> String toJson(T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(t);
    }

    protected ResultActions postResource(String path, Object resource) throws Exception {
        return mockMvc.perform(post(path).content(toJson(resource)).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    protected ResultActions getResource(String path) throws Exception {
        return mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    protected ResultActions putResource(String path, Object resource) throws Exception {
        return mockMvc.perform(put(path).content(toJson(resource)).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    protected ResultActions deleteResource(String path, Integer id) throws Exception {
        return mockMvc.perform(delete(path + "/" + id).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

}