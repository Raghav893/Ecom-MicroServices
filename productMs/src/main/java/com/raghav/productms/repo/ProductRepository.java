package com.raghav.productms.repo;

import com.raghav.productms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findProductByProductId(UUID productId);

    List<Product> findAllByCategoryIgnoreCase(String category);
}
