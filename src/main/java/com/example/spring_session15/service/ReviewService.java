package com.example.spring_session15.service;

import com.example.spring_session15.dto.request.ReviewRequest;
import com.example.spring_session15.entity.Review;
import java.util.List;

public interface ReviewService {
    Review createReview(ReviewRequest request);
    List<Review> getReviewsByProduct(Long productId);
}