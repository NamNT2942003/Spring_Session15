package com.example.spring_session15.service;

import com.example.spring_session15.dto.request.OrderRequest;
import com.example.spring_session15.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest request);
    List<Order> getMyOrders();
    List<Order> getAllOrders();
    Order updateOrderStatus(Long orderId, String newStatus);
}