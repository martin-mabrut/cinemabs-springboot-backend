package com.cinefamille.api.controller;

import com.cinefamille.api.dto.CreateReviewRequest;
import com.cinefamille.api.dto.ReviewDto;
import com.cinefamille.api.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // POST /api/reviews
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody CreateReviewRequest reviewRequest) {
        ReviewDto reviewCreated = reviewService.createReview(reviewRequest);
        return ResponseEntity.status(201).body(reviewCreated);
    }

    // PUT /api/reviews/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id, @Valid @RequestBody CreateReviewRequest reviewRequest) {
        ReviewDto reviewUpdated = reviewService.updateReview(id, reviewRequest);
        return ResponseEntity.ok(reviewUpdated);
    }

    // DELETE /api/reviews/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}