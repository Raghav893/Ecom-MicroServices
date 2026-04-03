package com.raghav.cartservice.service;

import com.raghav.cartservice.common.response.ApiResponse;
import com.raghav.cartservice.config.ProductFeignConfig;
import com.raghav.cartservice.entity.Cart;
import com.raghav.cartservice.entity.CartItem;
import com.raghav.cartservice.repo.CartItemRepository;
import com.raghav.cartservice.repo.CartRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductFeignConfig productFeignConfig;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductFeignConfig productFeignConfig
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productFeignConfig = productFeignConfig;
    }

    @Transactional
    public Cart addToCart(UUID productId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        ApiResponse<?> productResponse = productFeignConfig.getProductById(productId);
        if (productResponse == null || !productResponse.isSuccess() || productResponse.getData() == null) {
            throw new RuntimeException("Product not found");
        }

        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        Cart cart = cartRepository.getCartsByUserId(userEmail)
                .orElseGet(() -> createCart(userEmail));

        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        CartItem cartItem = cartItemRepository.findByCartAndProductId(cart, productId)
                .orElseGet(() -> createCartItem(cart, productId));

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        if (!cart.getItems().contains(savedCartItem)) {
            cart.getItems().add(savedCartItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }
    public Cart getMyCart(){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        Cart cart = cartRepository.getCartsByUserId(userEmail)
                .orElseGet(() -> createCart(userEmail));
        return cart;
    }

    @Transactional
    public Cart updateQuantity(UUID productId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        Cart cart = getCurrentUserCart();
        CartItem cartItem = cartItemRepository.findByCartAndProductId(cart, productId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeItem(UUID productId) {
        Cart cart = getCurrentUserCart();
        CartItem cartItem = cartItemRepository.findByCartAndProductId(cart, productId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        List<CartItem> items = cart.getItems();
        if (items != null) {
            items.removeIf(item -> item.getCartItemId().equals(cartItem.getCartItemId()));
        }

        cartItemRepository.delete(cartItem);
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }


    private Cart createCart(String userEmail) {
        Cart cart = new Cart();
        cart.setUserId(userEmail);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        cart.setItems(new ArrayList<>());
        return cartRepository.save(cart);
    }

    private CartItem createCartItem(Cart cart, UUID productId) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProductId(productId);
        cartItem.setQuantity(0);
        return cartItem;
    }

    private Cart getCurrentUserCart() {
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return cartRepository.getCartsByUserId(userEmail)
                .orElseGet(() -> createCart(userEmail));
    }
}
