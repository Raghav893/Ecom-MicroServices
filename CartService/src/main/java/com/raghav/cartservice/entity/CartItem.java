package com.raghav.cartservice.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartItemId;

    @Column(nullable = false)
    private UUID productId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
