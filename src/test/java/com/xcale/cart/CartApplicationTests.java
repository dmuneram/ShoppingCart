package com.xcale.cart;

import com.xcale.cart.resource.CartResource;
import com.xcale.cart.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CartApplicationTests {

    @Autowired
    private CartResource resource;

    @Autowired
    private CartService service;

    @Test
    void contextLoads() {
        assertNotNull(resource);
        assertNotNull(service);
    }

}
