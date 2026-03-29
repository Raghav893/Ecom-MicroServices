package com.raghav.productms.controller;

import com.raghav.productms.common.response.ApiResponse;
import com.raghav.productms.dto.AddProductDTO;
import com.raghav.productms.dto.UpdateProductDTO;
import com.raghav.productms.entity.Product;
import com.raghav.productms.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/admin/product")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {
    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> addProduct(@Valid @RequestBody AddProductDTO dto) {
        Product product = productService.addProduct(dto);
        return ResponseEntity.ok(ApiResponse.<Product>builder()
                .success(true)
                .message("Product created successfully")
                .data(product)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductDTO dto
    ) {
        Product product = productService.updateProduct(id, dto);
        return ResponseEntity.ok(ApiResponse.<Product>builder()
                .success(true)
                .message("Product updated successfully")
                .data(product)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable UUID id) {
        String message = productService.removeProduct(id);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(message)
                .build());
    }
}
