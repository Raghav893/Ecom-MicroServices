package com.raghav.productms.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateProductDTO {
    private String name;

    private String description;

    private String category;

    private String imageUrl;

    @PositiveOrZero
    private Double price;

    @PositiveOrZero
    private Integer stock;
}
