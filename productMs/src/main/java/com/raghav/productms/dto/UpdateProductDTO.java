package com.raghav.productms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateProductDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl;
    @PositiveOrZero
    private Double price;
    @PositiveOrZero
    private Integer stock;
}
