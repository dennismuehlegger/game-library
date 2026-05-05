package com.dennismuehlegger.gamelibrary.controller;

import com.dennismuehlegger.gamelibrary.entity.User;
import com.dennismuehlegger.gamelibrary.enums.PurchaseResult;
import com.dennismuehlegger.gamelibrary.repository.UserRepository;
import com.dennismuehlegger.gamelibrary.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
class UserController {


    private final UserService userService;

    UserController(UserRepository repository, UserService userService) {
        this.userService = userService;
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

    // todo - fix responses
    @GetMapping("/{userId}/history")
    public ResponseEntity<Void> getTransactionHistory(@PathVariable Long userId) {
        userService.getTransactionHistory(userId);
        return ResponseEntity.ok().build();
    }
}
