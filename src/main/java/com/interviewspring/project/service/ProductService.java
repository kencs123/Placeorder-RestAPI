package com.interviewspring.project.service;

import com.interviewspring.project.model.Product;
import com.interviewspring.project.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product>getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);}
}
