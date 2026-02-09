package com.cinefamille.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;


    private int rating;
    private String comment;
    private String photoUrl;


    public Review() {
    }

    public Review(User user, Movie movie, int rating, String photoUrl) {
        this.rating = rating;
        this.photoUrl = photoUrl;
        this.user = user;
        this.movie = movie;
    }


    public Long getId() { return id; }
    public User getUser() { return user; }
    public Movie getMovie() { return movie; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public String getPhotoUrl() { return photoUrl; }


    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public void setMovie (Movie movie) { this.movie = movie; }

}