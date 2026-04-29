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

    private final UserRepository repository;

    private final UserService userService;

    UserController(UserRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }


    @GetMapping
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.valueOf(id)));
    }

    @PutMapping("/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }

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
