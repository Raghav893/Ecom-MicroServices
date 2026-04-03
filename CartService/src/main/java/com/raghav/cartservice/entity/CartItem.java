package com.raghav.cartservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartItemId;


    private UUID productId;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
