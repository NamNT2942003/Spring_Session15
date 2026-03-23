package com.example.spring_session15.repository;

import com.example.spring_session15.entity.Order;
import com.example.spring_session15.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedDateDesc(User user);
}