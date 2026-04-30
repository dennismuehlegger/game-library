package com.dennismuehlegger.gamelibrary.controller;

import com.dennismuehlegger.gamelibrary.entity.User;
import com.dennismuehlegger.gamelibrary.repository.UserRepository;
import com.dennismuehlegger.gamelibrary.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // todo - fix responses
    @PostMapping("/{userId}/games/{gameId}/buy")
    public ResponseEntity<Void> buyGame(@PathVariable Long userId, @PathVariable Long gameId) {
        userService.buyGame(userId, gameId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<Void> getTransactionHistory(@PathVariable Long userId) {
        userService.getTransactionHistory(userId);
        return ResponseEntity.ok().build();
    }
}
