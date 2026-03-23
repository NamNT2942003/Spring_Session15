package com.example.spring_session15.controller;

import com.example.spring_session15.dto.request.ReviewRequest;
import com.example.spring_session15.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // POST /api/reviews
    @PostMapping("/reviews")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.createReview(request));
    }

    // GET /api/products/{id}/reviews
    @GetMapping("/products/{id}/reviews")
    public ResponseEntity<?> getProductReviews(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(id));
    }
}