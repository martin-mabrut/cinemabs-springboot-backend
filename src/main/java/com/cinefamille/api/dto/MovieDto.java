package com.cinefamille.api.dto;

public class MovieDto {

    private Long id;
    private String title;
    private String genre;
    private Integer year;
    private String synopsis;
    private String imageUrl;

    public MovieDto() {
    }

    public MovieDto(Long id, String title, String genre, Integer year, String synopsis, String imageUrl) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.synopsis = synopsis;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public Integer getYear() { return year; }
    public String getSynopsis() { return synopsis; }
    public String getImageUrl() { return imageUrl; }
}
