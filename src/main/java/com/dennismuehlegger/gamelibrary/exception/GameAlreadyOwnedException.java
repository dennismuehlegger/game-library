package com.dennismuehlegger.gamelibrary.exception;

public class GameAlreadyOwnedException extends RuntimeException {
    public GameAlreadyOwnedException(String message) {
        super(message);
    }
}
