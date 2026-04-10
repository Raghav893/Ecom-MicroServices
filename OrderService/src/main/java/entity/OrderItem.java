package entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Embeddable
@Data
public class OrderItem {

    private UUID productId;
    private Integer quantity;
}