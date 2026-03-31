package com.raghav.cartservice.client.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductResponse {

    private UUID productId;
    private String name;
    private String description;
    private String category;
    private Double price;
    private Integer stock;
    private String imageUrl;
}
