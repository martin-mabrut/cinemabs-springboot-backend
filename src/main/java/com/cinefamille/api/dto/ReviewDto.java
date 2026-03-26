package com.cinefamille.api.dto;

public class ReviewDto {

    private Long id;
    private Long userId;
    private String username;
    private Long movieId;
    private String movieTitle;
    private int rating;
    private String comment;
    private String photoUrl;

    public ReviewDto() {
    }

    public ReviewDto(Long id, Long userId, String username, Long movieId, String movieTitle, int rating, String comment, String photoUrl) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.rating = rating;
        this.comment = comment;
        this.photoUrl = photoUrl;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public Long getMovieId() { return movieId; }
    public String getMovieTitle() { return movieTitle; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public String getPhotoUrl() { return photoUrl; }
}
