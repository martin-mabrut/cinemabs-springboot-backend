package com.cinefamille.api.service;

import com.cinefamille.api.model.Movie;
import com.cinefamille.api.model.Review;
import com.cinefamille.api.model.User;
import com.cinefamille.api.repository.ReviewRepository;
import com.cinefamille.api.repository.UserRepository;
import com.cinefamille.api.repository.MovieRepository;
import com.cinefamille.api.dto.ReviewDto;
import com.cinefamille.api.dto.CreateReviewRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import com.cinefamille.api.exception.ResourceNotFoundException;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(review -> toDto(review))
                .toList();
    }

    public ReviewDto getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(review -> toDto(review))
                .orElseThrow(() -> new ResourceNotFoundException("Critique non trouvée avec l'id : " + id));
    }

    public ReviewDto createReview(CreateReviewRequest reviewRequest) {
        Review review = new Review(
                userRepository.findById(reviewRequest.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id : " + reviewRequest.getUserId())),
                movieRepository.findById(reviewRequest.getMovieId())
                        .orElseThrow(() -> new ResourceNotFoundException("Film non trouvé avec l'id : " + reviewRequest.getMovieId())),
                reviewRequest.getRating(),
                reviewRequest.getComment(),
                reviewRequest.getPhotoUrl()
        );
        Review reviewSaved = reviewRepository.save(review);
        return toDto(reviewSaved);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewDto> getReviewsByMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException ("Film non trouvé avec l'id : " + movieId));
        return reviewRepository.findByMovie(movie)
                .stream()
                .map(review -> toDto(review))
                .toList();
    }

    public List<ReviewDto> getReviewsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException ("Utilisateur non trouvé avec l'id : " + userId));
        return reviewRepository.findByUser(user)
                .stream()
                .map(review -> toDto(review))
                .toList();
    }

    public ReviewDto updateReview(Long id, CreateReviewRequest updatedReview) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Critique non trouvée avec l'id : " + id));
        existing.setRating(updatedReview.getRating());
        existing.setComment(updatedReview.getComment());
        existing.setPhotoUrl(updatedReview.getPhotoUrl());
        Review reviewSaved = reviewRepository.save(existing);
        return toDto(reviewSaved);
    }

    public ReviewDto toDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getUsername(),
                review.getMovie().getId(),
                review.getMovie().getTitle(),
                review.getRating(),
                review.getComment(),
                review.getPhotoUrl()
        );
    }
}