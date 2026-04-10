package com.raghav.orderservice.config;

import com.raghav.orderservice.common.response.ApiResponse;
import com.raghav.orderservice.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "cartService",url = "localhost:8083",
configuration = FeignAuthConfig.class
)
public interface CartFeignClient {
    @GetMapping("/cart")
     ApiResponse<CartDTO> getcart();

    @DeleteMapping("/cart")
    ApiResponse<CartDTO> deletecart();

}
