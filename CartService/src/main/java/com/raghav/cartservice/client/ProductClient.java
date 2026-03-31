package com.raghav.cartservice.client;

import com.raghav.cartservice.common.response.ApiResponse;
import com.raghav.cartservice.client.config.ProductFeignConfig;
import com.raghav.cartservice.client.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "product-service",
        url = "${services.product-service.url:http://localhost:8081}",
        configuration = ProductFeignConfig.class
)
public interface ProductClient {

    @GetMapping("/user/product/{productId}")
    ApiResponse<ProductResponse> getProductById(@PathVariable("productId") java.util.UUID productId);
}
