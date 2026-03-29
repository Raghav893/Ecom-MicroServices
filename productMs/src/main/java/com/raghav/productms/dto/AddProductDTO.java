package com.raghav.productms.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class AddProductDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    @NotBlank
    @JsonAlias("imagUrl")
    private String imageUrl;

    @PositiveOrZero
    private double price;

    @PositiveOrZero
    private int stock;
}
