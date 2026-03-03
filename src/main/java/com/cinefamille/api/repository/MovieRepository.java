package com.cinefamille.api.repository;

import com.cinefamille.api.model.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenre(String genre);

    List<Movie> findByTitle(String title);
}