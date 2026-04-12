package com.raghav.orderservice.controller;

import com.raghav.orderservice.common.response.ApiResponse;
import com.raghav.orderservice.service.OrderService;
import entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user/orders")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class OrderUserController {

    private final OrderService orderService;

    public OrderUserController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> placeOrder() {
        Order order = orderService.placeOrder();

        ApiResponse<Order> response = ApiResponse.<Order>builder()
                .success(true)
                .message("Order placed successfully")
                .data(order)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable UUID id) {
        Order order = orderService.getOrderById(id);

        ApiResponse<Order> response = ApiResponse.<Order>builder()
                .success(true)
                .message("Order fetched successfully")
                .data(order)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getMyOrders() {
        List<Order> orders = orderService.getMyOrders();

        ApiResponse<List<Order>> response = ApiResponse.<List<Order>>builder()
                .success(true)
                .message("Orders fetched successfully")
                .data(orders)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> cancelOrder(@PathVariable UUID id) {
        String result = orderService.cancelOrder(id);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Order cancelled successfully")
                .data(result)
                .build();

        return ResponseEntity.ok(response);
    }
}
