package com.dennismuehlegger.Entitites;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private int releaseYear;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @OneToMany(mappedBy = "game")
    private List<Library> libraries;
}
