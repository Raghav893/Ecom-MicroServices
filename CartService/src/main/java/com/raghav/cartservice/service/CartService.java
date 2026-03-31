package com.raghav.cartservice.service;

import com.raghav.cartservice.common.security.AuthenticatedUser;
import com.raghav.cartservice.model.Cart;
import com.raghav.cartservice.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductGateway productGateway;

    public void addToCart(String productId, int quantity) {
        UUID userId = getCurrentUserId();
        validateUserId(userId);
        validateProductId(productId);

        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than 0");
        }

        productGateway.ensureProductExists(productId);

        String cartKey = buildCartKey(userId);
        Integer existingQuantity = getQuantity(cartKey, productId);
        int updatedQuantity = existingQuantity + quantity;

        redisTemplate.opsForHash().put(cartKey, productId, updatedQuantity);
    }

    public void updateQuantity(String productId, int quantity) {
        UUID userId = getCurrentUserId();
        validateUserId(userId);
        validateProductId(productId);

        if (quantity <= 0) {
            removeItem(productId);
            return;
        }

        productGateway.ensureProductExists(productId);

        redisTemplate.opsForHash().put(buildCartKey(userId), productId, quantity);
    }

    public void removeItem(String productId) {
        UUID userId = getCurrentUserId();
        validateUserId(userId);
        validateProductId(productId);

        redisTemplate.opsForHash().delete(buildCartKey(userId), productId);
    }

    public Cart getCart() {
        UUID userId = getCurrentUserId();
        validateUserId(userId);

        Map<Object, Object> cartEntries = redisTemplate.opsForHash().entries(buildCartKey(userId));
        if (cartEntries == null || cartEntries.isEmpty()) {
            return new Cart(userId, Collections.emptyList());
        }

        List<CartItem> items = cartEntries.entrySet().stream()
                .map(entry -> new CartItem(String.valueOf(entry.getKey()), toInteger(entry.getValue())))
                .toList();

        return new Cart(userId, items);
    }

    public void clearCart() {
        UUID userId = getCurrentUserId();
        validateUserId(userId);
        redisTemplate.delete(buildCartKey(userId));
    }

    private Integer getQuantity(String cartKey, String productId) {
        Object value = redisTemplate.opsForHash().get(cartKey, productId);
        return value == null ? 0 : toInteger(value);
    }

    private Integer toInteger(Object value) {
        if (value instanceof Integer integerValue) {
            return integerValue;
        }

        if (value instanceof Number number) {
            return number.intValue();
        }

        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException exception) {
            throw new IllegalStateException("Invalid quantity stored in cart");
        }
    }

    private String buildCartKey(UUID userId) {
        return "cart:" + userId;
    }

    private void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }
    }

    private void validateProductId(String productId) {
        if (!StringUtils.hasText(productId)) {
            throw new IllegalArgumentException("productId is required");
        }
    }

    private UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser user)) {
            throw new IllegalStateException("Authenticated user not found");
        }
        if (user.userId() == null) {
            throw new IllegalStateException("Authenticated user id not found in token");
        }
        return user.userId();
    }
}
