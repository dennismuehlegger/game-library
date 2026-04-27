package com.dennismuehlegger.gamelibrary.controller;

import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.repository.GameRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
class GameController {

    private final GameRepository repository;

    GameController(GameRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    List<Game> all() {
        return repository.findAll();
    }

    @PostMapping
    Game newGame(@RequestBody Game newGame) {
        return repository.save(newGame);
    }

    @GetMapping("/{id}")
    Game getGame(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.valueOf(id)));
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
}

