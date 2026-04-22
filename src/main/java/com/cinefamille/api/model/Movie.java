package com.cinefamille.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String genre;

    private Integer year;

    private String synopsis;
    private String imageUrl;

    public Movie() {
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public Integer getYear() { return year; }
    public String getSynopsis() { return synopsis; }
    public String getImageUrl() { return imageUrl; }

    // Setters
    public void setId(Long id) { this.id = id; } // used in tests only
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setYear(Integer year) { this.year = year; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
