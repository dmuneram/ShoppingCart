package com.xcale.cart.service;

import com.xcale.cart.entity.Cart;
import com.xcale.cart.entity.CartDetail;
import com.xcale.cart.entity.Product;
import com.xcale.cart.exception.BusinessException;
import com.xcale.cart.model.CartDTO;
import com.xcale.cart.model.CartDetailDTO;
import com.xcale.cart.repository.CartRepository;
import com.xcale.cart.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository repository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService service;

    private final Cart entity = new Cart();

    @Test
    void getCartInformation() {
        // Arrange
        when(repository.findCartByCartId(anyString())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        String cartId = UUID.randomUUID().toString();

        // Act
        Cart cartInformation = service.getCartInformation(cartId);

        // Assert
        assertNotNull(cartInformation);
    }

    @Test
    void getCartInformation_notFound() {
        // Arrange
        String cartId = UUID.randomUUID().toString();

        // Act & Assert
        assertThrows(BusinessException.class, () -> service.getCartInformation(cartId));

    }

    @Test
    void save() {
        // Arrange
        Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(repository.save(any())).thenReturn(entity);
        CartDTO cartDTO = new CartDTO();
        Set<CartDetailDTO> items = new HashSet<>();
        CartDetailDTO dtoDetail = new CartDetailDTO();
        dtoDetail.setProductId(1L);
        dtoDetail.setQuantity(1);
        items.add(dtoDetail);
        cartDTO.setCartItems(items);

        // Act
        Cart saved = service.save(cartDTO);

        // Assert
        assertNotNull(saved);
    }

    @Test
    void save_invalidProduct() {
        // Arrange
        CartDTO cartDTO = new CartDTO();
        Set<CartDetailDTO> items = new HashSet<>();
        CartDetailDTO dtoDetail = new CartDetailDTO();
        dtoDetail.setProductId(1L);
        dtoDetail.setQuantity(1);
        items.add(dtoDetail);
        cartDTO.setCartItems(items);

        // Act & Assert
        assertThrows(BusinessException.class, () -> service.save(cartDTO));

    }

    @Test
    void save_existingCart() {
        // Arrange
        String id = UUID.randomUUID().toString();
        entity.setCartId(id);
        when(repository.findCartByCartId(anyString())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(id);
        cartDTO.setCartItems(new HashSet<>());

        // Act
        Cart saved = service.save(cartDTO);

        // Assert
        assertNotNull(saved);
    }

    @Test
    void save_existingCart_notFound() {
        // Arrange
        String id = UUID.randomUUID().toString();
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(id);
        cartDTO.setCartItems(new HashSet<>());

        // Act & Assert
        assertThrows(BusinessException.class, () -> service.save(cartDTO));

    }

    @Test
    void delete() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        when(repository.findCartByCartId(anyString())).thenReturn(Optional.of(entity));

        // Act & Assert
        assertDoesNotThrow(() -> service.delete(cartId));

    }

    @Test
    void delete_NotFound() {
        // Arrange
        String cartId = UUID.randomUUID().toString();

        // Act & Assert
        assertThrows(BusinessException.class, () -> service.delete(cartId));

    }

    @Test
    void deleteOldCarts() {
        // Act & Assert
        assertDoesNotThrow(() -> service.deleteOldCarts());
    }

    @Test
    void addDetail_successfully() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        CartDetailDTO request = new CartDetailDTO();
        request.setQuantity(1);
        request.setProductId(1L);
        entity.setCartItems(new HashSet<>());
        Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(repository.findCartByCartId(anyString())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);

        // Act
        Cart saved = service.addDetail(cartId, request);

        // Assert
        assertNotNull(saved);
    }

    @Test
    void addDetail_successfully_existing_item() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        CartDetailDTO request = new CartDetailDTO();
        request.setQuantity(1);
        request.setProductId(1L);
        Set<CartDetail> items = new HashSet<>();
        CartDetail detail = new CartDetail();
        Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        product.setId(1L);
        detail.setProduct(product);
        items.add(detail);
        entity.setCartItems(items);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(repository.findCartByCartId(anyString())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);

        // Act
        Cart saved = service.addDetail(cartId, request);

        // Assert
        assertNotNull(saved);
    }

    @Test
    void addDetail_invalidProduct() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        CartDetailDTO request = new CartDetailDTO();
        request.setQuantity(1);
        request.setProductId(null);

        // Act & Assert
        assertThrows(BusinessException.class, () -> service.addDetail(cartId, request));

    }

    @Test
    void addDetail_cartNotFound() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        CartDetailDTO request = new CartDetailDTO();
        request.setQuantity(1);
        request.setProductId(1L);
        Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        product.setId(1L);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // Act & Assert
        assertThrows(BusinessException.class, () -> service.addDetail(cartId, request));

    }
}