package com.dennismuehlegger.GameLibrary;

import com.dennismuehlegger.Entitites.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
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
}
