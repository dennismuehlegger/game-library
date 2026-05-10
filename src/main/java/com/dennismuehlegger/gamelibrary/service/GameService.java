package com.dennismuehlegger.gamelibrary.service;

import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public Game update(Long id, Game newGame) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setName(newGame.getName());
                    game.setPrice(newGame.getPrice());
                    game.setReleaseYear(newGame.getReleaseYear());
                    return gameRepository.save(game);
                })
                .orElseGet(() -> gameRepository.save(newGame));
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    public List<Game> filterGames(List<Game> games, Integer releaseYear, BigDecimal minPrice, BigDecimal maxPrice, BigDecimal exactPrice, String name) {
        return games.stream()
                .filter(game -> releaseYear == null || game.getReleaseYear() == releaseYear)
                .filter(game -> exactPrice == null || game.getPrice().compareTo(exactPrice) == 0)
                .filter(game -> exactPrice != null || minPrice == null || game.getPrice().compareTo(minPrice) >= 0)
                .filter(game -> exactPrice != null || maxPrice == null || game.getPrice().compareTo(maxPrice) <= 0)
                .filter(game -> name == null || game.getName().equalsIgnoreCase(name))
                .toList();
    }

    public List<Game> sortGames(List<Game> games, Boolean releaseYear, Boolean price, Boolean name, Boolean descending) {
        Comparator<Game> comparator = (game1, game2) -> 0;

        if (releaseYear != null && releaseYear) {
            comparator = Comparator.comparing(Game::getReleaseYear);
        }
        if (price != null && price) {
            comparator = comparator.thenComparing(Game::getPrice);
        }
        if (name != null && name) {
            comparator = comparator.thenComparing(Game::getName);
        }

        if (descending != null && descending) {
            comparator = comparator.reversed();
        }

        return games.stream().sorted(comparator).toList();
    }
}
