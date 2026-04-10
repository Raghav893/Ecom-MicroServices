package com.raghav.orderservice.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CartDTO {
    @NotNull
    private UUID cartId;

    @Email
    @NotBlank
    private String userId;

    @NotBlank
    private LocalDateTime createdAt;
    @NotBlank
    private LocalDateTime updatedAt;

    @NotBlank
    private List<CartItemDTO> items;
}
