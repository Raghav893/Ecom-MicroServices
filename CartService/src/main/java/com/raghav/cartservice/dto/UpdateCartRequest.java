package com.raghav.cartservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartRequest {

    @NotBlank(message = "productId is required")
    private String productId;

    private int quantity;
}
