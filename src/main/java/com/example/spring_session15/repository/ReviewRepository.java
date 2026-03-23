package com.example.spring_session15.repository;

import com.example.spring_session15.entity.Product;
import com.example.spring_session15.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductOrderByCreatedDateDesc(Product product);
}