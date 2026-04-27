package com.dennismuehlegger.gamelibrary.service;

import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.entity.Library;
import com.dennismuehlegger.gamelibrary.entity.User;
import com.dennismuehlegger.gamelibrary.repository.UserRepository;
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

    public void buyGame(Long userId, Long gameId) {
        Optional<User> userOptional = getUserById(userId);
        Optional<Game> gameOptional = gameService.getGameById(gameId);

        if (userOptional.isPresent() && gameOptional.isPresent()) {
            User user = userOptional.get();
            Game game = gameOptional.get();

            user.getLibraries().forEach(library -> {
                if (library.getGame().getId().equals(game.getId())) {
                    System.out.println("Game already exists in the library.");
                }
            });
            if (user.getFunds() < game.getPrice()) {
                System.out.println("Not enough funds to buy the game.");
            }
            else {
                user.getLibraries().add(new Library() {{
                    setGame(game);
                    setUser(user);
                }});
                System.out.println("Game bought successfully.");
                System.out.println(user.getLibraries());
            }
        } else {
            System.out.println("placeholder");
        }
    }
}
