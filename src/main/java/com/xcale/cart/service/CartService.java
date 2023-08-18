package com.xcale.cart.service;

import com.xcale.cart.entity.Cart;
import com.xcale.cart.entity.CartDetail;
import com.xcale.cart.entity.Product;
import com.xcale.cart.exception.BusinessException;
import com.xcale.cart.model.CartDTO;
import com.xcale.cart.model.CartDetailDTO;
import com.xcale.cart.repository.CartRepository;
import com.xcale.cart.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.xcale.cart.constant.AppConstants.CART_NOT_FOUND;
import static com.xcale.cart.constant.AppConstants.INVALID_PRODUCT;

@Service
public class CartService {

    private final CartRepository repository;
    private final ProductRepository productRepository;

    public CartService(CartRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public Cart getCartInformation(String id) {
        Cart cart = this.repository.findCartByCartId(id)
                .orElseThrow(() -> new BusinessException(String.format(CART_NOT_FOUND, id)));
        cart.setLastUsed(LocalDateTime.now());
        return repository.save(cart);
    }

    @Transactional
    public Cart save(CartDTO request) {
        Cart cart;
        if (request.getId() == null) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
        } else {
            cart = repository.findCartByCartId(request.getId())
                    .orElseThrow(() -> new BusinessException(String.format(CART_NOT_FOUND, request.getId())));
        }
        cart.setCartItems(new HashSet<>());
        BigDecimal cartTotal = BigDecimal.ZERO;
        for (CartDetailDTO detailDTO : request.getCartItems()) {
            Product product = productRepository.findById(detailDTO.getProductId()).orElse(null);
            if (product == null) throw new BusinessException(INVALID_PRODUCT);
            CartDetail detail = new CartDetail();
            detail.setProduct(product);
            detail.setPrice(product.getPrice());
            detail.setQuantity(detailDTO.getQuantity());
            BigDecimal totalDetail = product.getPrice().multiply(BigDecimal.valueOf(detailDTO.getQuantity()));
            detail.setTotal(totalDetail);
            cartTotal = cartTotal.add(totalDetail);
            cart.getCartItems().add(detail);
        }
        cart.setCartTotal(cartTotal);
        cart.setLastUsed(LocalDateTime.now());
        return repository.save(cart);
    }

    @Transactional
    public void delete(String cartId) {
        Cart cart = repository.findCartByCartId(cartId)
                .orElseThrow(() -> new BusinessException(String.format(CART_NOT_FOUND, cartId)));
        repository.delete(cart);
    }

    @Transactional
    public void deleteOldCarts() {
        repository.deleteAllByLastUsedBefore(LocalDateTime.now().minusMinutes(10));
    }

    public Cart addDetail(String cartId, CartDetailDTO request) {
        Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) throw new BusinessException(INVALID_PRODUCT);

        Cart cart = repository.findCartByCartId(cartId)
                .orElseThrow(() -> new BusinessException(String.format(CART_NOT_FOUND, cartId)));


        Optional<CartDetail> foundDetail = cart.getCartItems()
                .stream()
                .filter(cartDetail -> Objects.equals(cartDetail.getProduct().getId(), request.getProductId()))
                .findFirst();

        BigDecimal detailTotal = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        if (foundDetail.isPresent()) {
            CartDetail detail = foundDetail.get();
            detail.setPrice(product.getPrice());
            detail.setQuantity(request.getQuantity());
            detail.setTotal(detailTotal);
        } else {
            CartDetail newDetail = new CartDetail();
            newDetail.setQuantity(request.getQuantity());
            newDetail.setPrice(product.getPrice());
            newDetail.setProduct(product);
            newDetail.setTotal(detailTotal);
            cart.getCartItems().add(newDetail);
        }
        BigDecimal calculatedTotal = cart.getCartItems()
                .stream()
                .map(CartDetail::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setCartTotal(calculatedTotal);
        cart.setLastUsed(LocalDateTime.now());
        return repository.save(cart);
    }
}
