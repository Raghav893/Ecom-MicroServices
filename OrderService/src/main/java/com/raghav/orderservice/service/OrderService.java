package com.raghav.orderservice.service;

import com.raghav.orderservice.common.response.ApiResponse;
import com.raghav.orderservice.config.CartFeignClient;
import com.raghav.orderservice.config.ProductFeignConfig;
import com.raghav.orderservice.dto.CartDTO;
import com.raghav.orderservice.dto.CartItemDTO;
import com.raghav.orderservice.dto.OrderStatusDTO;
import com.raghav.orderservice.dto.ProductDTO;
import com.raghav.orderservice.entity.Order;
import com.raghav.orderservice.entity.OrderItem;
import com.raghav.orderservice.entity.Status;
import com.raghav.orderservice.repo.OrderRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.raghav.orderservice.entity.Status.DELIVERED;
import static com.raghav.orderservice.entity.Status.SHIPPED;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartFeignClient cartFeignClient;
    private final ProductFeignConfig productFeignConfig;

    public OrderService(
            OrderRepository orderRepository,
            CartFeignClient cartFeignClient,
            ProductFeignConfig productFeignConfig
    ) {
        this.orderRepository = orderRepository;
        this.cartFeignClient = cartFeignClient;
        this.productFeignConfig = productFeignConfig;
    }

    public Order placeOrder() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        ApiResponse<CartDTO> cartResponse = cartFeignClient.getcart();
        CartDTO cart = cartResponse.getData();

        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<CartItemDTO> cartItems = cart.getItems();

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(item.getProductId());
                    orderItem.setQuantity(item.getQuantity());
                    return orderItem;
                })
                .toList();

        double totalAmount = 0.0;
        for (CartItemDTO item : cartItems) {
            ProductDTO productDTO = productFeignConfig
                    .getProductById(item.getProductId())
                    .getData();

            if (productDTO == null) {
                throw new RuntimeException("Product not found: " + item.getProductId());
            }

            totalAmount += productDTO.getPrice() * item.getQuantity();
        }

        Order order = new Order();
        order.setUserMail(username);
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(Status.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        cartFeignClient.deletecart();

        return savedOrder;
    }

    public Order getOrderById(UUID id) {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUserMail().equals(userMail)) {
            throw new RuntimeException("you dont have any order with this order id");
        }

        return order;
    }

    public List<Order> getMyOrders() {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderRepository.findOrderByUserMail(userMail);
    }

    public Order updateOrderStatus(OrderStatusDTO dto, UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(dto.getStatus());
        return orderRepository.save(order);
    }

    public String cancelOrder(UUID id) {
        Order order = getOrderById(id);
        if (order.getStatus().equals(SHIPPED) || order.getStatus().equals(DELIVERED)) {
            throw new RuntimeException("Order cannot be cancelled ");
        }

        orderRepository.delete(order);
        return "order deleted";
    }
}
