package com.raghav.cartservice.controller;

import com.raghav.cartservice.common.response.ApiResponse;
import com.raghav.cartservice.dto.AddToCartDTO;
import com.raghav.cartservice.entity.Cart;
import com.raghav.cartservice.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Cart>> AddToCart(@RequestBody AddToCartDTO dto) {

        Cart cart = cartService.addToCart(dto);
        return ResponseEntity.ok(ApiResponse.<Cart>builder()
                        .success(true)
                        .message("Product added to  cart")
                .data(cart)
                        .errors(null)
                .build());

    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Cart>> getcart() {
        Cart cart = cartService.getMyCart();
        return ResponseEntity.ok(ApiResponse.<Cart>builder()
                        .success(true)
                .message("Your cart is ")
                .data(cart)
                .errors(null)
                .build());
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<Cart>> UpdateCartQuantity(@RequestBody AddToCartDTO dto) {
        Cart cart = cartService.updateQuantity(dto);
        return ResponseEntity.ok(ApiResponse.<Cart>builder()
                        .success(true)
                .message(" cart updated")
                .data(cart)
                .errors(null)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Cart>> removeProduct(@PathVariable UUID id) {
        Cart cart = cartService.removeItem(id);
        return ResponseEntity.ok(ApiResponse.<Cart>builder()
                        .success(true)
                .message(" cart updated")
                .data(cart)
                .errors(null)
                .build());
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse<Cart>> clearCart() {
        Cart cart = cartService.clearCart();
        return ResponseEntity.ok(ApiResponse.<Cart>builder()
                .success(true)
                .message(" cart cleared")
                .data(cart)
                .errors(null)
                .build());
    }
    //Testing left


}
