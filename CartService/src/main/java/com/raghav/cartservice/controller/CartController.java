package com.raghav.cartservice.controller;

import com.raghav.cartservice.common.response.ApiResponse;
import com.raghav.cartservice.dto.AddToCartRequest;
import com.raghav.cartservice.dto.UpdateCartRequest;
import com.raghav.cartservice.model.Cart;
import com.raghav.cartservice.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ApiResponse<Void> addToCart(@Valid @RequestBody AddToCartRequest request) {
        cartService.addToCart(request.getUserId(), request.getProductId(), request.getQuantity());

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Item added to cart")
                .data(null)
                .errors(List.of())
                .build();
    }

    @PutMapping("/update")
    public ApiResponse<Void> updateQuantity(@Valid @RequestBody UpdateCartRequest request) {
        cartService.updateQuantity(request.getUserId(), request.getProductId(), request.getQuantity());

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Cart updated successfully")
                .data(null)
                .errors(List.of())
                .build();
    }

    @DeleteMapping("/remove/{productId}")
    public ApiResponse<Void> removeItem(
            @RequestParam UUID userId,
            @PathVariable String productId
    ) {
        cartService.removeItem(userId, productId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Item removed from cart")
                .data(null)
                .errors(List.of())
                .build();
    }

    @GetMapping
    public ApiResponse<Cart> getCart(@RequestParam UUID userId) {
        Cart cart = cartService.getCart(userId);

        return ApiResponse.<Cart>builder()
                .success(true)
                .message("Cart fetched successfully")
                .data(cart)
                .errors(List.of())
                .build();
    }

    @DeleteMapping("/clear")
    public ApiResponse<Void> clearCart(@RequestParam UUID userId) {
        cartService.clearCart(userId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Cart cleared successfully")
                .data(null)
                .errors(List.of())
                .build();
    }
}
