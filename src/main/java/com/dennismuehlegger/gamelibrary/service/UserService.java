package com.dennismuehlegger.gamelibrary.service;

import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.entity.Library;
import com.dennismuehlegger.gamelibrary.entity.User;
import com.dennismuehlegger.gamelibrary.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final GameService gameService;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, GameService gameService) {
        this.userRepository = userRepository;
        this.gameService = gameService;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void buyGame(Long userId, Long gameId) {
        Optional<User> userOptional = getUserById(userId);
        Optional<Game> gameOptional = gameService.getGameById(gameId);

        if (userOptional.isEmpty() || gameOptional.isEmpty()) {
            return;
        }

        User user = userOptional.get();
        Game game = gameOptional.get();

        boolean alreadyOwned = user.getLibraries().stream()
                .anyMatch(l -> l.getGame().getId().equals(game.getId()));

        if (alreadyOwned) {
            System.out.println("Game already exists in the library.");
            return;
        }

        if (user.getFunds() < game.getPrice()) {
            System.out.println("Not enough funds.");
            return;
        }

        Library library = new Library();
        library.setGame(game);
        library.setUser(user);

        user.getLibraries().add(library);
        user.setFunds(user.getFunds() - game.getPrice());

        userRepository.save(user);
    }

    public void getTransactionHistory(Long userId){
        Optional<User> userOptional = getUserById(userId);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.getLibraries().forEach(library -> {
                System.out.println(user + " has bought " + library.getGame().getName() + " for " + library.getGame().getPrice() + "€");
            });
        }
    }
}
