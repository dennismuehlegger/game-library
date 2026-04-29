package com.dennismuehlegger.gamelibrary.controller;

import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.repository.GameRepository;
import com.dennismuehlegger.gamelibrary.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
class GameController {

    private final GameRepository repository;

    private final GameService gameService;

    GameController(GameRepository repository, GameService gameService) {
        this.repository = repository;
        this.gameService = gameService;
    }

    @PostMapping
    Game newGame(@RequestBody Game newGame) {
        return repository.save(newGame);
    }

    @GetMapping
    public List<Game> getGames(
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double exactPrice,
            @RequestParam(required = false) String name
    ) {
        List<Game> games = repository.findAll();
        return gameService.filterGames(games, releaseYear, minPrice, maxPrice, exactPrice, name);
    }

    @PutMapping("/{id}")
    Game replaceGame(@RequestBody Game newGame, @PathVariable Long id) {

        return repository.findById(id)
                .map(game -> {
                    game.setName(newGame.getName());
                    game.setPrice(newGame.getPrice());
                    game.setReleaseYear(newGame.getReleaseYear());
                    return repository.save(game);
                })
                .orElseGet(() -> {
                    return repository.save(newGame);
                });
    }

    @DeleteMapping("/{id}")
    void deleteGame(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/sort")
    public List<Game> sortGames(@RequestParam(required = false) Boolean releaseYear,
                                        @RequestParam(required = false) Boolean price,
                                        @RequestParam(required = false) Boolean name,
                                @RequestParam(required = false) Boolean descending) {
        List<Game> games = repository.findAll();
        return gameService.sortGames(games, releaseYear, price, name, descending);
    }
}

