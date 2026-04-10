package com.raghav.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;


import java.util.UUID;

@Data
public class CartItemDTO
{
    private UUID cartItemId;


    private UUID productId;

    private int quantity;


    @JsonBackReference
    private CartDTO cart;
}
