package com.dennismuehlegger.gamelibrary.controller;

import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.repository.GameRepository;
import com.dennismuehlegger.gamelibrary.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
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
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) BigDecimal exactPrice,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean sortByPrice,
            @RequestParam(required = false) Boolean sortByName,
            @RequestParam(required = false) Boolean sortByReleaseYear,
            @RequestParam(required = false) Boolean descending
    ) {
        List<Game> games = gameService.findAll();
        games = gameService.filterGames(games, releaseYear, minPrice, maxPrice, exactPrice, name);
        return gameService.sortGames(games, sortByReleaseYear, sortByPrice, sortByName, descending);
    }

    @PutMapping("/{id}")
    Game replaceGame(@RequestBody Game newGame, @PathVariable Long id) {
        return gameService.update(id, newGame);
    }

    @DeleteMapping("/{id}")
    void deleteGame(@PathVariable Long id) {
        gameService.delete(id);
    }
}

