package com.xcale.cart.resource;

import com.xcale.cart.exception.BusinessException;
import com.xcale.cart.model.CartDetailRequestDTO;
import com.xcale.cart.model.CartRequestDTO;
import com.xcale.cart.model.CartResponseDTO;
import com.xcale.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Cart resource controller handles Rest API operations
 * - fetch cart
 * - create new cart
 * - add new item to an existing cart
 * - delete cart using respective identifier
 */
@RestController
@RequestMapping("/cart")
public class CartResource {

    private final Logger logger = LoggerFactory.getLogger(CartResource.class);
    private final CartService service;

    public CartResource(CartService service) {
        this.service = service;
    }


    @Operation(summary = "Retrieve cart information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Cart retrieved",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Some error occurred while fetching cart",
                    content = @Content)})
    @GetMapping(value = "/{cartId}")
    public ResponseEntity<CartResponseDTO> fetchCart(@PathVariable String cartId) {
        CartResponseDTO entity = service.getCartInformation(cartId);
        logger.info("Cart retrieved!");
        return ResponseEntity.ok(entity);
    }

    @Operation(summary = "Delete cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Cart deleted.",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Some error occurred while deleting cart",
                    content = @Content)})
    @DeleteMapping(value = "/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable String cartId) {
        service.delete(cartId);
        logger.info("Cart deleted!");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create or update item in existing cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Item saved successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Cart not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Some error occurred while saving cart item",
                    content = @Content)})
    @PostMapping(value = "/{cartId}")
    public ResponseEntity<CartResponseDTO> saveItemToCart(@PathVariable String cartId, @RequestBody CartDetailRequestDTO request) {

        if (request.getProductId() == null || request.getProductId() < 1)
            throw new BusinessException("Product Id is mandatory");
        if (request.getQuantity() < 1)
            throw new BusinessException("Quantity is mandatory and value must be greater than 0");
        CartResponseDTO cart = service.addDetail(cartId, request);

        logger.info("Cart saved");

        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Create or replace existing cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Cart saved successfully",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Some error occurred while saving cart",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<CartResponseDTO> saveCart(@RequestBody CartRequestDTO request) {
        validateCart(request);
        CartResponseDTO cart = service.save(request);

        logger.info("Cart saved");

        return ResponseEntity.ok(cart);
    }

    private void validateCart(CartRequestDTO request) {
        if (request.getCartItems() == null) throw new BusinessException("Error creating cart, items were empty.");
        boolean invalid = request.getCartItems()
                .stream()
                .anyMatch(dto -> dto.getProductId() == null || dto.getProductId() < 1 || dto.getQuantity() < 1);
        if (invalid) throw new BusinessException("Error creating cart, items have missing productId or quantity.");
    }
}
