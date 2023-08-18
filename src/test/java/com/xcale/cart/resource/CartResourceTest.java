package com.xcale.cart.resource;

import com.xcale.cart.entity.Cart;
import com.xcale.cart.exception.RestExceptionHandler;
import com.xcale.cart.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartResourceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Mock
    private CartService service;

    @InjectMocks
    private CartResource resource;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(resource)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    void fetchCart() throws Exception {
        when(service.getCartInformation(anyString())).thenReturn(new Cart());
        mockMvc.perform(get("/cart/cartId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
        verify(service, times(1)).getCartInformation(anyString());
    }

    @Test
    void deleteCart() throws Exception {
        mockMvc.perform(delete("/cart/cartId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().is2xxSuccessful());
        verify(service, times(1)).delete(anyString());
    }

    @Test
    void saveItemToCart() throws Exception {
        mockMvc.perform(post("/cart/cartId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 1, \"quantity\": 1}"))
                .andExpect(status().is2xxSuccessful());
        verify(service, times(1)).addDetail(anyString(), any());
    }

    @Test
    void saveItemToCart_invalidProduct_null() throws Exception {
        mockMvc.perform(post("/cart/cartId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": null, \"quantity\": 1}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveItemToCart_invalidProduct_0() throws Exception {
        mockMvc.perform(post("/cart/cartId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 0, \"quantity\": 1}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveItemToCart_invalidQuantity_0() throws Exception {
        mockMvc.perform(post("/cart/cartId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 1, \"quantity\": 0}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveCart() throws Exception {
        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cartItems\": [{\"productId\": 1, \"quantity\": 1}]}"))
                .andExpect(status().isOk());
        verify(service, times(1)).save(any());
    }

    @Test
    void saveCart_itemsEmptyValidation() throws Exception {
        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveCart_itemsProductNullValidation() throws Exception {
        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cartItems\": [{\"productId\": null, \"quantity\": 1}]}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveCart_itemsProductZeroValidation() throws Exception {
        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cartItems\": [{\"productId\": 0, \"quantity\": 1}]}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveCart_itemsQuantityZeroValidation() throws Exception {
        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cartItems\": [{\"productId\": 1, \"quantity\": 0}]}"))
                .andExpect(status().is4xxClientError());
    }
}