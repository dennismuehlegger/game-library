package com.dennismuehlegger.gamelibrary.exception;

public class GameNotOwnedException extends RuntimeException {
    public GameNotOwnedException(String message) {
        super(message);
    }
}
