package com.example.spring_session15.service.impl;

import com.example.spring_session15.dto.request.ReviewRequest;
import com.example.spring_session15.entity.Product;
import com.example.spring_session15.entity.Review;
import com.example.spring_session15.entity.User;
import com.example.spring_session15.repository.OrderRepository;
import com.example.spring_session15.repository.ProductRepository;
import com.example.spring_session15.repository.ReviewRepository;
import com.example.spring_session15.repository.UserRepository;
import com.example.spring_session15.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    @Transactional
    public Review createReview(ReviewRequest request) {
        User user = getCurrentUser();

        // Validate 1: Người dùng đã mua hàng (và đơn hàng đã COMPLETED) chưa?
        boolean isBought = orderRepository.hasUserBoughtProduct(user.getId(), request.getProductId());
        if (!isBought) {
            throw new RuntimeException("Bạn chỉ có thể đánh giá sản phẩm đã mua thành công!");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedDate(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        return reviewRepository.findByProductOrderByCreatedDateDesc(product);
    }
}