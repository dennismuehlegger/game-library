package com.dennismuehlegger.gamelibrary.service;

import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }
}
