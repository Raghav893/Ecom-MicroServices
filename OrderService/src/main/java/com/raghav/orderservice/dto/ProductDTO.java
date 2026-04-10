package com.raghav.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductDTO {
    @NotNull
    private UUID productId;

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    @NotBlank
    private double price;
    @NotBlank
    private int stock;
    @NotBlank
    private String imageUrl;

}
