package com.cinefamille.api.repository;

import com.cinefamille.api.model.User;
import com.cinefamille.api.model.Movie;
import com.cinefamille.api.model.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovie(Movie movie);

    List<Review> findByUser(User user);
}