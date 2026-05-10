package com.dennismuehlegger.gamelibrary.dto;

public class TransactionItemDTO {
    private String gameName;
    private double price;

    public TransactionItemDTO(String gameName, double price) {
        this.gameName = gameName;
        this.price = price;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
