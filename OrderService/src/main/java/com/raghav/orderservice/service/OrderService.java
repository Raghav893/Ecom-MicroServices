package com.raghav.orderservice.service;

import com.raghav.orderservice.common.response.ApiResponse;
import com.raghav.orderservice.config.CartFeignClient;
import com.raghav.orderservice.config.ProductFeignConfig;
import com.raghav.orderservice.dto.CartDTO;
import com.raghav.orderservice.dto.CartItemDTO;
import com.raghav.orderservice.dto.ProductDTO;
import com.raghav.orderservice.repo.OrderRepository;
import entity.Order;
import entity.OrderItem;
import entity.Status;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartFeignClient cartFeignClient;
    private final ProductFeignConfig productFeignConfig;
    public OrderService(OrderRepository orderRepository, CartFeignClient cartFeignClient, ProductFeignConfig productFeignConfig) {
        this.orderRepository = orderRepository;
        this.cartFeignClient = cartFeignClient;
        this.productFeignConfig = productFeignConfig;
    }
    public Order placeOrder() {

        // 1. Get userId
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Fetch cart from Cart Service
        ApiResponse<CartDTO> cartResponse = cartFeignClient.getcart();
        CartDTO cart = cartResponse.getData();

        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<CartItemDTO> itemDTOS = cart.getItems();

        // 3. Convert CartItemDTO → OrderItem
        List<OrderItem> orderItems = itemDTOS.stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(item.getProductId());
                    orderItem.setQuantity(item.getQuantity());
                    return orderItem;
                })
                .toList();

        // 4. Calculate total
        double totalAmount = 0.0;

        for (CartItemDTO item : itemDTOS) {

            ProductDTO productDTO = productFeignConfig
                    .getProductById(item.getProductId())
                    .getData();

            if (productDTO == null) {
                throw new RuntimeException("Product not found: " + item.getProductId());
            }

            double price = productDTO.getPrice();
            int quantity = item.getQuantity();

            totalAmount += price * quantity;
        }
        // 5. Create Order
        Order order = new Order();
        order.setUserMail(username);
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(Status.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        // 6. Save Order
        Order savedOrder = orderRepository.save(order);

        // 7. Clear cart
        cartFeignClient.deletecart();

        return savedOrder;
    }

    public Order getOrderById (UUID id){
        String usermail = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUserMail().equals(usermail)) {
            throw new RuntimeException("you dont have any order with this order id");
        }
        return order;

        }
    }


