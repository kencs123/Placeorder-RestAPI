package com.interviewspring.project.service;

import com.interviewspring.project.model.Order;
import com.interviewspring.project.model.OrderItem;
import com.interviewspring.project.model.Product;
import com.interviewspring.project.repository.OrderItemRepository;
import com.interviewspring.project.repository.OrderRepository;
import com.interviewspring.project.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order createOrder(Order order) {

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItem item : order.getOrderItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            item.setProduct(product);
            item.setOrder(order);
            totalPrice = totalPrice.add(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
    public void deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new EntityNotFoundException("Order not found with id: " + orderId);
        }
    }

    // Method to retrieve all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }
}