package com.cinefamille.api.controller;

import com.cinefamille.api.model.Movie;
import com.cinefamille.api.model.Review;
import com.cinefamille.api.model.User;
import com.cinefamille.api.service.MovieService;
import com.cinefamille.api.service.ReviewService;
import com.cinefamille.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final MovieService movieService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService, MovieService movieService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.movieService = movieService;
    }

    // POST /api/reviews?movieId=3&userId=1
    @PostMapping
    public ResponseEntity<Review> createReview(
            @RequestParam Long movieId,
            @RequestParam Long userId,
            @RequestBody Review review) {

        Optional<Movie> movie = movieService.getMovieById(movieId);
        Optional<User> user = userService.getUserById(userId);

        if (movie.isEmpty() || user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Review reviewFinal = new Review(user.get(), movie.get(), review.getRating(), review.getPhotoUrl());
        reviewFinal.setComment(review.getComment());
        Review created = reviewService.createReview(reviewFinal);
        return ResponseEntity.status(201).body(created);
    }

    // PUT /api/reviews/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Review reviewUpdated = reviewService.updateReview(id, review);
        return ResponseEntity.ok(reviewUpdated);
    }

    // DELETE /api/reviews/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}