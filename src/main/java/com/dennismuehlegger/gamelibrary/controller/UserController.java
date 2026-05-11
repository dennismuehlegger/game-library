package com.dennismuehlegger.gamelibrary.controller;

import com.dennismuehlegger.gamelibrary.dto.LibraryDTO;
import com.dennismuehlegger.gamelibrary.dto.TransactionHistoryDTO;
import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.entity.User;
import com.dennismuehlegger.gamelibrary.enums.PlayResult;
import com.dennismuehlegger.gamelibrary.enums.PurchaseResult;
import com.dennismuehlegger.gamelibrary.repository.UserRepository;
import com.dennismuehlegger.gamelibrary.service.GameService;
import com.dennismuehlegger.gamelibrary.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
class UserController {


    private final UserService userService;
    private final GameService gameService;

    UserController(UserRepository repository, UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }


    @GetMapping
    List<User> all() {
        return userService.findAll();
    }

    @PostMapping
    User newUser(@RequestBody User newUser) {
        return userService.create(newUser);
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable Long id) {

        return userService.findById(id);
    }

    @PutMapping("/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return userService.update(id, newUser);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping("/{userId}/games/{gameId}/buy")
    public ResponseEntity<PurchaseResult> buyGame(@PathVariable Long userId, @PathVariable Long gameId) {
        switch (userService.buyGame(userId, gameId)) {
            case SUCCESS -> {
                return ResponseEntity.ok(PurchaseResult.SUCCESS);
            }
            case INSUFFICIENT_FUNDS -> {
                return ResponseEntity.badRequest().body(PurchaseResult.INSUFFICIENT_FUNDS);
            }
            case GAME_ALREADY_OWNED -> {
                return ResponseEntity.badRequest().body(PurchaseResult.GAME_ALREADY_OWNED);
            }
            case USER_OR_GAME_NOT_FOUND -> {
                return ResponseEntity.notFound().build();
            }
        }
        return null;
    }

    @GetMapping("/{userId}/library")
    public ResponseEntity<List<LibraryDTO>> getUserLibrary(@PathVariable Long userId, @RequestParam(required = false) Integer releaseYear,
                                                           @RequestParam(required = false) BigDecimal minPrice,
                                                           @RequestParam(required = false) BigDecimal maxPrice,
                                                           @RequestParam(required = false) BigDecimal exactPrice,
                                                           @RequestParam(required = false) String name,
                                                           @RequestParam(required = false) Boolean sortByReleaseYear,
                                                           @RequestParam(required = false) Boolean sortByPrice,
                                                           @RequestParam(required = false) Boolean sortByName,
                                                           @RequestParam(required = false) Boolean descending) {
        List<Game> games = userService.getUserLibrary(userId);
        games = gameService.filterGames(games, releaseYear, minPrice, maxPrice, exactPrice, name);
        games = gameService.sortGames(games, sortByReleaseYear, sortByPrice, sortByName, descending);
        List<LibraryDTO> result = userService.mapToLibraryDTO(userId, games);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{userId}/games/{gameId}/play")
    public ResponseEntity<PlayResult> playGame(@PathVariable Long userId, @PathVariable Long gameId) {
        switch (userService.playGame(userId, gameId)) {
            case SUCCESS -> {
                return ResponseEntity.ok(PlayResult.SUCCESS);
            }
            case USER_OR_GAME_NOT_FOUND -> {
                return ResponseEntity.badRequest().body(PlayResult.USER_OR_GAME_NOT_FOUND);
            }
            case GAME_NOT_OWNED -> {
                return ResponseEntity.badRequest().body(PlayResult.GAME_NOT_OWNED);
            }
        }
        return null;
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<TransactionHistoryDTO> getTransactionHistory(@PathVariable Long userId) {
        TransactionHistoryDTO transactionHistoryDTO = userService.getTransactionHistory(userId);
        switch (transactionHistoryDTO.getResult()) {
            case SUCCESS -> {
                return ResponseEntity.ok(transactionHistoryDTO);
            }
            case NO_HISTORY -> {
                return ResponseEntity.badRequest().body(transactionHistoryDTO);
            }
            case NO_USER -> {
                return ResponseEntity.notFound().build();
            }
        }
        return null;
    }
}
