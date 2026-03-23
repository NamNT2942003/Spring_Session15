package com.example.spring_session15.service.impl;

import com.example.spring_session15.dto.request.OrderRequest;
import com.example.spring_session15.entity.Order;
import com.example.spring_session15.entity.OrderItem;
import com.example.spring_session15.entity.Product;
import com.example.spring_session15.entity.User;
import com.example.spring_session15.repository.OrderRepository;
import com.example.spring_session15.repository.ProductRepository;
import com.example.spring_session15.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // Hàm tiện ích lấy User đang đăng nhập từ Security Context
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không xác định được người dùng hiện tại"));
    }

    @Transactional
    public Order createOrder(OrderRequest request) {
        User currentUser = getCurrentUser();
        Order order = new Order();
        order.setUser(currentUser);
        order.setCreatedDate(LocalDateTime.now());
        order.setStatus("PENDING");

        BigDecimal totalMoney = BigDecimal.ZERO;

        for (OrderRequest.ItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + itemReq.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order); // Link item vào order
            orderItem.setProduct(product);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPriceBuy(product.getPrice()); // Chốt giá tại thời điểm mua

            // Tính tổng tiền
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalMoney = totalMoney.add(itemTotal);

            order.getItems().add(orderItem);
        }

        order.setTotalMoney(totalMoney);
        return orderRepository.save(order);
    }

    public List<Order> getMyOrders() {
        return orderRepository.findByUserOrderByCreatedDateDesc(getCurrentUser());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + orderId));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}