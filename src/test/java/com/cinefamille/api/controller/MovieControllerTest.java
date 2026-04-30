package com.cinefamille.api.controller;

import com.cinefamille.api.dto.CreateMovieRequest;
import com.cinefamille.api.dto.MovieDto;
import com.cinefamille.api.service.MovieService;
import com.cinefamille.api.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private MovieService movieService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private MovieController movieController;

    // @BeforeEach s'exécute avant chaque test — on reconstruit MockMvc autour du controller
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        objectMapper = new ObjectMapper();
    }

    // -------------------------------------------------------------------------
    // GET /api/movies
    // -------------------------------------------------------------------------

    // Exemple complet — lis-le attentivement avant de faire les suivants.
    @Test
    void getAllMovies_shouldReturn200WithList() throws Exception {
        // ARRANGE
        MovieDto movieDto = new MovieDto(1L, "Inception", "Science-Fiction", 2010, null, null);
        when(movieService.getAllMovies()).thenReturn(List.of(movieDto));

        // ACT + ASSERT
        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Inception"));
    }

    // -------------------------------------------------------------------------
    // GET /api/movies/{id}
    // -------------------------------------------------------------------------

    @Test
    void getMovieById_shouldReturn200_whenMovieExists() throws Exception {
        // ARRANGE
        // TODO : crée un MovieDto avec id=1L, title="Inception"
        MovieDto movieDto = new MovieDto(1L, "Inception", "Science-Fiction", 2010, null, null);
        // TODO : dis au mock que movieService.getMovieById(1L) retourne ce DTO
        when(movieService.getMovieById(1L)).thenReturn(movieDto);

        // ACT + ASSERT
        // TODO : simule GET /api/movies/1
        mockMvc.perform(get("/api/movies/1"))
        // TODO : vérifie status 200
                .andExpect(status().isOk())
        // TODO : vérifie que $.title vaut "Inception"
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    // -------------------------------------------------------------------------
    // POST /api/movies
    // -------------------------------------------------------------------------

    @Test
    void createMovie_shouldReturn201_whenRequestIsValid() throws Exception {
        // ARRANGE
        // TODO : crée un CreateMovieRequest avec title="Dune", genre="Science-Fiction", year=2021
        CreateMovieRequest createMovieRequest = new CreateMovieRequest();
        createMovieRequest.setTitle("Dune");
        createMovieRequest.setGenre("Science-Fiction");
        createMovieRequest.setYear(2021);
        // TODO : crée un MovieDto "created" avec id=1L et les mêmes valeurs
        MovieDto created = new MovieDto(1L, "Dune", "Science-Fiction", 2021, null, null);
        // TODO : dis au mock que movieService.createMovie(any()) retourne ce DTO
        when(movieService.createMovie(any())).thenReturn(created);

        // ACT + ASSERT
        // TODO : simule POST /api/movies avec le body en JSON :
        //        mockMvc.perform(post("/api/movies")
        //               .contentType(MediaType.APPLICATION_JSON)
        //               .content(objectMapper.writeValueAsString(request)))
        mockMvc.perform(post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMovieRequest)))
        // TODO : vérifie status 201
                .andExpect(status().isCreated())
        // TODO : vérifie que $.title vaut "Dune"
                .andExpect(jsonPath("$.title").value("Dune"));
    }

    // -------------------------------------------------------------------------
    // DELETE /api/movies/{id}
    // -------------------------------------------------------------------------

    @Test
    void deleteMovie_shouldReturn204() throws Exception {
        // ACT + ASSERT
        // TODO : simule DELETE /api/movies/1
        mockMvc.perform(delete("/api/movies/1"))
        // TODO : vérifie status 204
                .andExpect(status().isNoContent());
    }
}
