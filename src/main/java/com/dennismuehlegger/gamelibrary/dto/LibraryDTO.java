package com.dennismuehlegger.gamelibrary.dto;

import java.math.BigDecimal;

public class LibraryDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private int releaseYear;
    private String coverArtUrl;
    private int hoursPlayed;

    public LibraryDTO() {
    }

    public LibraryDTO(Long id, String name, BigDecimal price, int releaseYear, String coverArtUrl, int hoursPlayed) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.releaseYear = releaseYear;
        this.coverArtUrl = coverArtUrl;
        this.hoursPlayed = hoursPlayed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCoverArtUrl() {
        return coverArtUrl;
    }

    public void setCoverArtUrl(String coverArtUrl) {
        this.coverArtUrl = coverArtUrl;
    }

    public int getHoursPlayed() {
        return hoursPlayed;
    }

    public void setHoursPlayed(int hoursPlayed) {
        this.hoursPlayed = hoursPlayed;
    }
}
