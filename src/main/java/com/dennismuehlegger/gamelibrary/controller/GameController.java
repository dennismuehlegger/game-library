package com.dennismuehlegger.gamelibrary.controller;

import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.repository.GameRepository;
import com.dennismuehlegger.gamelibrary.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
class GameController {

    private final GameService gameService;

    GameController(GameRepository repository, GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    Game newGame(@RequestBody Game newGame) {
        return gameService.create(newGame);
    }

    @GetMapping
    public List<Game> getGames(
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double exactPrice,
            @RequestParam(required = false) String name
    ) {
        List<Game> games = gameService.findAll();
        return gameService.filterGames(games, releaseYear, minPrice, maxPrice, exactPrice, name);
    }

    @PutMapping("/{id}")
    Game replaceGame(@RequestBody Game newGame, @PathVariable Long id) {
        return gameService.update(id, newGame);
    }

    @DeleteMapping("/{id}")
    void deleteGame(@PathVariable Long id) {
        gameService.delete(id);
    }

    @PostMapping("/sort")
    public List<Game> sortGames(@RequestParam(required = false) Boolean releaseYear,
                                        @RequestParam(required = false) Boolean price,
                                        @RequestParam(required = false) Boolean name,
                                @RequestParam(required = false) Boolean descending) {
        List<Game> games = gameService.findAll();
        return gameService.sortGames(games, releaseYear, price, name, descending);
    }
}

