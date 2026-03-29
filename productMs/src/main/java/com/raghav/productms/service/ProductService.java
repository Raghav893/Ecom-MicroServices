package com.raghav.productms.service;

import com.raghav.productms.dto.AddProductDTO;
import com.raghav.productms.dto.UpdateProductDTO;
import com.raghav.productms.entity.Product;
import com.raghav.productms.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(AddProductDTO dto) {
        Product product = new Product();
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setName(dto.getName());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setPrice(dto.getPrice());
        return productRepository.save(product);
    }

    public String removeProduct(UUID id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        return "product deleted " + product.getName();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(UUID id, UpdateProductDTO dto) {
        Product product = getProductById(id);

        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getCategory() != null) {
            product.setCategory(dto.getCategory());
        }
        if (dto.getName() != null) {
            product.setName(dto.getName());
        }
        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }
        if (dto.getImageUrl() != null) {
            product.setImageUrl(dto.getImageUrl());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }

        return productRepository.save(product);
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> products = productRepository.findAllByCategoryIgnoreCase(category);
        if (products.isEmpty()) {
            throw new RuntimeException("No products found for category: " + category);
        }
        return products;
    }
}
