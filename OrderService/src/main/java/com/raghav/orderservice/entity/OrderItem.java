package com.raghav.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Embeddable
@Data
public class OrderItem {

    @Column(name = "product_id")
    private UUID productId;

    private Integer quantity;
}
