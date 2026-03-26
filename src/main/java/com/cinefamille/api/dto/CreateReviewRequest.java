package com.cinefamille.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateReviewRequest {

    @NotNull(message = "Une critique doit concerner un Film")
    private Long movieId;

    @NotNull(message = "Erreur d'identification")
    private Long userId;

    @Min(value = 1, message = "La note doit être comprise entre 1 et 10")
    @Max(value = 10, message = "La note doit être comprise entre 1 et 10")
    private int rating;

    private String comment;
    private String photoUrl;

    public CreateReviewRequest() {
    }

    public Long getMovieId() { return movieId; }
    public Long getUserId() { return userId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public String getPhotoUrl() { return photoUrl; }

    public void setMovieId(Long newMovieId) { this.movieId = newMovieId; }
    public void setUserId(Long newUserId) { this.userId = newUserId; }
    public void setRating(int newRating) { this.rating = newRating; }
    public void setComment(String newComment) { this.comment = newComment; }
    public void setPhotoUrl(String newPhotoUrl) { this.photoUrl = newPhotoUrl; }

}
