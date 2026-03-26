package com.cinefamille.api.service;

import com.cinefamille.api.model.Movie;
import com.cinefamille.api.repository.MovieRepository;
import com.cinefamille.api.dto.MovieDto;
import com.cinefamille.api.dto.CreateMovieRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import com.cinefamille.api.exception.ResourceNotFoundException;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movie -> toDto(movie))
                .toList();
    }

    public MovieDto getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(movie -> toDto(movie))
                .orElseThrow(() -> new ResourceNotFoundException("Film non trouvé avec l'id : " + id));
    }

    public MovieDto createMovie(CreateMovieRequest movieRequest) {
        Movie movie = new Movie();
        movie.setTitle(movieRequest.getTitle());
        movie.setGenre(movieRequest.getGenre());
        movie.setYear(movieRequest.getYear());
        movie.setSynopsis(movieRequest.getSynopsis());
        movie.setImageUrl(movieRequest.getImageUrl());
        Movie saved = movieRepository.save(movie);
        return toDto(saved);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public List<MovieDto> getMoviesByGenre(String genre) {

        return movieRepository.findByGenre(genre)
                .stream()
                .map(movie -> toDto(movie))
                .toList();
    }

    private MovieDto toDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getYear(),
                movie.getSynopsis(),
                movie.getImageUrl()
        );
    }
}