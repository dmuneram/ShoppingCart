package com.xcale.cart.resource;

import com.xcale.cart.model.ProductDTO;
import com.xcale.cart.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Product resource controller handles Rest API operations
 *  - Retrieve all products
 */
@RestController
@RequestMapping("/product")
public class ProductResource {

    private final Logger logger = LoggerFactory.getLogger(ProductResource.class);

    private final ProductService service;

    public ProductResource(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Retrieve all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of products",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Some error occurred while fetching products",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<ProductDTO>> fetchAllProducts() {
        List<ProductDTO> list = service.findAll();
        logger.info("List of products retrieved.");
        return ResponseEntity.ok(list);
    }

}
