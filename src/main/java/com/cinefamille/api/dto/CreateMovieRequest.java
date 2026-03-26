package com.cinefamille.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateMovieRequest {

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

    public CreateMovieRequest() {
    }

    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public Integer getYear() { return year; }
    public String getSynopsis() { return synopsis; }
    public String getImageUrl() { return imageUrl; }

    public void setTitle(String newTitle) { this.title = newTitle; }
    public void setGenre(String newGenre) { this.genre = newGenre; }
    public void setYear(Integer newYear) { this.year = newYear; }
    public void setSynopsis(String newSynopsis) { this.synopsis = newSynopsis; }
    public void setImageUrl(String newImageUrl) { this.imageUrl = newImageUrl; }
}
