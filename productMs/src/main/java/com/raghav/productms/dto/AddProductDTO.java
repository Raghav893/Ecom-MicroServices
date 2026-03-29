package com.raghav.productms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class AddProductDTO {
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String imagUrl;

    @NotNull
    @PositiveOrZero
    private double price;

    @NotNull
    @PositiveOrZero
    private int stock;
}
