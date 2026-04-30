package com.cinefamille.api.controller;

import com.cinefamille.api.dto.CreateReviewRequest;
import com.cinefamille.api.dto.ReviewDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
        objectMapper = new ObjectMapper();
    }

    // -------------------------------------------------------------------------
    // POST /api/reviews
    // -------------------------------------------------------------------------

    @Test
    void createReview_shouldReturn201_whenRequestIsValid() throws Exception {
        // ARRANGE
        // TODO : crée un CreateReviewRequest avec userId=1L, movieId=2L, rating=8, comment="Super"
        CreateReviewRequest createReviewRequest = new CreateReviewRequest();
        createReviewRequest.setUserId(1L);
        createReviewRequest.setMovieId(2L);
        createReviewRequest.setRating(8);
        createReviewRequest.setComment("Super");
        // TODO : crée un ReviewDto "created" avec id=1L, rating=8, comment="Super"
        //        (hint: le constructeur de ReviewDto attend : id, userId, username, movieId, movieTitle, rating, comment, photoUrl)
        ReviewDto created = new ReviewDto(1L, 1L,"martin", 2L, "Dune", 8, "Super", null);
        // TODO : dis au mock que reviewService.createReview(any()) retourne ce DTO
        when(reviewService.createReview(any())).thenReturn(created);

        // ACT + ASSERT
        // TODO : simule POST /api/reviews avec le body en JSON
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createReviewRequest)))
        // TODO : vérifie status 201
                .andExpect(status().isCreated())
        // TODO : vérifie $.rating vaut 8
                .andExpect(jsonPath("$.rating").value(8));
    }

    // -------------------------------------------------------------------------
    // PUT /api/reviews/{id}
    // -------------------------------------------------------------------------

    @Test
    void updateReview_shouldReturn200_whenRequestIsValid() throws Exception {
        // ARRANGE
        // TODO : crée un CreateReviewRequest avec rating=9, comment="Encore mieux"
        CreateReviewRequest createReviewRequest = new CreateReviewRequest();
        createReviewRequest.setUserId(1L);
        createReviewRequest.setMovieId(2L);
        createReviewRequest.setRating(9);
        createReviewRequest.setComment("Encore mieux");
        // TODO : crée un ReviewDto "updated" avec id=1L, rating=9, comment="Encore mieux"
        ReviewDto updated = new ReviewDto(1L, 1L,"martin", 2L, "Dune", 9, "Encore mieux", null);
        // TODO : dis au mock que reviewService.updateReview(eq(1L), any()) retourne ce DTO
        when(reviewService.updateReview(eq(1L),any())).thenReturn(updated);

        // ACT + ASSERT
        // TODO : simule PUT /api/reviews/1 avec le body en JSON
        mockMvc.perform(put("/api/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createReviewRequest)))
        // TODO : vérifie status 200
                .andExpect(status().isOk())
        // TODO : vérifie $.rating vaut 9
                .andExpect(jsonPath("$.rating").value(9));
    }

    // -------------------------------------------------------------------------
    // DELETE /api/reviews/{id}
    // -------------------------------------------------------------------------

    @Test
    void deleteReview_shouldReturn204() throws Exception {
        // ACT + ASSERT
        // TODO : simule DELETE /api/reviews/1
        mockMvc.perform(delete("/api/reviews/1"))
        // TODO : vérifie status 204
                .andExpect(status().isNoContent());
    }
}