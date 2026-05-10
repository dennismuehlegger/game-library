package com.dennismuehlegger.gamelibrary.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;
    private int releaseYear;
    private String coverArtUrl;

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

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Library> libraries;

    @Override
    public String toString() {
        return "game " + getName();
    }
}
