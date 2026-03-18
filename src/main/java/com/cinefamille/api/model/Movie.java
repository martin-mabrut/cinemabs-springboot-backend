package com.cinefamille.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.*;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotBlank(message = "Le genre est obligatoire")
    private String genre;

    @NotNull(message = "L'année est obligatoire")
    @Min(value = 1888, message = "Veuillez renseigner une année supérieure à 1888")
    @Max(value = 2100, message = "Veuillez renseigner une année inférieure à 2100")
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
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setYear(Integer year) { this.year = year; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
