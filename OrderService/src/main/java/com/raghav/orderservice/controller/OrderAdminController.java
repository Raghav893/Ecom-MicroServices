package com.raghav.orderservice.controller;

import com.raghav.orderservice.common.response.ApiResponse;
import com.raghav.orderservice.dto.OrderStatusDTO;
import com.raghav.orderservice.service.OrderService;
import entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class OrderAdminController {

    private final OrderService orderService;

    public OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(
            @RequestBody OrderStatusDTO dto,
            @PathVariable UUID id
    ) {
        Order order = orderService.UpdateOrderStatus(dto, id);

        ApiResponse<Order> response = ApiResponse.<Order>builder()
                .success(true)
                .message("Order status updated successfully")
                .data(order)
                .build();

        return ResponseEntity.ok(response);
    }
}
