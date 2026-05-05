package com.dennismuehlegger.gamelibrary.service;

import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.entity.Library;
import com.dennismuehlegger.gamelibrary.entity.User;
import com.dennismuehlegger.gamelibrary.enums.PurchaseResult;
import com.dennismuehlegger.gamelibrary.repository.GameRepository;
import com.dennismuehlegger.gamelibrary.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public User update(Long id, User newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                })
                .orElseGet(() -> userRepository.save(newUser));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public PurchaseResult buyGame(Long userId, Long gameId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Game> gameOptional = gameRepository.findById(gameId);

        if (userOptional.isEmpty() || gameOptional.isEmpty()) {
            return PurchaseResult.USER_OR_GAME_NOT_FOUND;
        }

        User user = userOptional.get();
        Game game = gameOptional.get();

        boolean alreadyOwned = user.getLibraries().stream()
                .anyMatch(l -> l.getGame().getId().equals(game.getId()));

        if (alreadyOwned) {
            return PurchaseResult.GAME_ALREADY_OWNED;
        }

        if (game.getPrice() > user.getFunds()) {
            return PurchaseResult.INSUFFICIENT_FUNDS;
        }

        Library library = new Library();
        library.setGame(game);
        library.setUser(user);

        user.getLibraries().add(library);
        user.setFunds(user.getFunds() - game.getPrice());

        userRepository.save(user);
        return PurchaseResult.SUCCESS;
    }

    public void getTransactionHistory(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getLibraries().forEach(library -> {
                System.out.println(user + " has bought " + library.getGame().getName() + " for " + library.getGame().getPrice() + "€");
            });
        }
    }
}
