package com.dennismuehlegger.gamelibrary.service;

import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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

    // todo - make this a more complex filter with min max price for example
    public List<Game> filterGames(List<Game> games, Integer releaseYear, Double price, String name) {
        return games.stream()
                .filter(game -> releaseYear == null || game.getReleaseYear() == releaseYear)
                .filter(game -> price == null || game.getPrice() == price)
                .filter(game -> name == null || game.getName().equals(name))
                .toList();
    }

    public List<Game> sortGames(List<Game> games, Boolean releaseYear, Boolean price, Boolean name) {
        return games.stream()
                .sorted(Comparator.comparing((Game game) -> releaseYear != null && releaseYear ? game.getReleaseYear() : null,
                                Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing((Game game) -> price != null && price ? game.getPrice() : null,
                                Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing((Game game) -> name != null && name ? game.getName() : null,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }
}
