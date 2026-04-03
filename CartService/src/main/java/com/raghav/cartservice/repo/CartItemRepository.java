package com.raghav.cartservice.repo;

import com.raghav.cartservice.entity.Cart;
import com.raghav.cartservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> findByCartAndProductId(Cart cart, UUID productId);
}
