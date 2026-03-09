package com.cinefamille.api.service;

import com.cinefamille.api.model.Movie;
import com.cinefamille.api.model.Review;
import com.cinefamille.api.model.User;
import com.cinefamille.api.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsByMovie(Movie movie) {
        return reviewRepository.findByMovie(movie);
    }

    public List<Review> getReviewsByUser(User user) {
        return reviewRepository.findByUser(user);
    }

    public Review updateReview(Long id, Review updatedReview) {
        Review existing = reviewRepository.findById(id).orElseThrow();
        existing.setRating(updatedReview.getRating());
        existing.setComment(updatedReview.getComment());
        existing.setPhotoUrl(updatedReview.getPhotoUrl());
        return reviewRepository.save(existing);
    }
}