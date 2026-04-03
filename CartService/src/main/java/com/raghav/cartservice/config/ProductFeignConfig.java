package com.raghav.cartservice.config;

import com.raghav.cartservice.common.response.ApiResponse;
import com.raghav.cartservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "productMs",url = "http://localhost:8082"
)
public interface ProductFeignConfig {
    @GetMapping("/user/product/{id}")
     ApiResponse<ProductDTO> getProductById(@PathVariable UUID id);
}
