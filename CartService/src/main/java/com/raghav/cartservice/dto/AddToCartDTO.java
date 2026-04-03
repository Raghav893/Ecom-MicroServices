package com.raghav.cartservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AddToCartDTO {
    UUID productId;
    int quantity;
}
