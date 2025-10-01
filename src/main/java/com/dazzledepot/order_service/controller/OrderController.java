package com.dazzledepot.order_service.controller;

import com.dazzledepot.order_service.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dazzledepot.order_service.repository.OrderRepository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class OrderController {
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private RestTemplate restTemplate;
    @PostMapping("/place")
    public Order placeOrder(@RequestBody Order order) {
        if (order.getItems() == null) order.setItems(new ArrayList<>());
        if (order.getTrackingUpdates() == null) order.setTrackingUpdates(new ArrayList<>());
        order.setStatus("pending");
        order.getTrackingUpdates().add(new Order.TrackingUpdate("pending", new Date().toString(), "Order placed"));
        // Optional: Validate items with Product Service
        order.getItems().forEach(item -> {
            try {
                restTemplate.getForObject("http://localhost:8082/api/products/" + item.getProductId(), Object.class);
            } catch (Exception e) {
                throw new RuntimeException("Invalid product: " + item.getProductId());
            }
        });
        return orderRepo.save(order);
    }

    @GetMapping("/{userId}")
    public List<Order> getOrders(@PathVariable String userId) {
        return orderRepo.findByUserId(userId);
    }

    @GetMapping("/track/{orderId}")
    public Order getOrderDetails(@PathVariable String orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
    }
}
