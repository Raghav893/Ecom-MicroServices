package com.raghav.productms.controller;

import com.raghav.productms.common.response.ApiResponse;
import com.raghav.productms.entity.Product;
import com.raghav.productms.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user/product")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserProductController {
    private final ProductService productService;

    public UserProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getProducts(
            @RequestParam(required = false) String category
    ) {
        List<Product> products = category == null || category.isBlank()
                ? productService.getAllProducts()
                : productService.getProductsByCategory(category);

        return ResponseEntity.ok(ApiResponse.<List<Product>>builder()
                .success(true)
                .message("Products fetched successfully")
                .data(products)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.<Product>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(product)
                .build());
    }
}
