package com.dennismuehlegger.gamelibrary.dto;

import java.math.BigDecimal;

public class TransactionItemDTO {
    private String gameName;
    private BigDecimal price;

    public TransactionItemDTO(String gameName, BigDecimal price) {
        this.gameName = gameName;
        this.price = price;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
