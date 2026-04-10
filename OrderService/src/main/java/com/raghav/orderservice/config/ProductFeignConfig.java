package com.raghav.orderservice.config;


import com.raghav.orderservice.common.response.ApiResponse;
import com.raghav.orderservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "productMs",
        url = "http://localhost:8082",
        configuration = FeignAuthConfig.class
)
public interface ProductFeignConfig {
    @GetMapping("/user/product/{id}")
    ApiResponse<ProductDTO> getProductById(@PathVariable UUID id);
}
