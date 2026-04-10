package entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItem {

    private String productId;
    private Integer quantity;
}