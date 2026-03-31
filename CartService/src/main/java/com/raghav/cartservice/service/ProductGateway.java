package com.raghav.cartservice.service;

import com.raghav.cartservice.client.ProductClient;
import com.raghav.cartservice.client.dto.ProductResponse;
import com.raghav.cartservice.common.response.ApiResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductGateway {

    private final ProductClient productClient;

    public void ensureProductExists(String productId) {
        UUID parsedProductId = parseProductId(productId);

        try {
            ApiResponse<ProductResponse> response = productClient.getProductById(parsedProductId);
            ProductResponse product = response == null ? null : response.getData();

            if (product == null || product.getProductId() == null) {
                throw new IllegalArgumentException("Product not found: " + productId);
            }
        } catch (FeignException.NotFound exception) {
            throw new IllegalArgumentException("Product not found: " + productId);
        }
    }

    private UUID parseProductId(String productId) {
        try {
            return UUID.fromString(productId);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("productId must be a valid UUID");
        }
    }
}
