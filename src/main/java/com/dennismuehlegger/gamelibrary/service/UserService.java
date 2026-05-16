package com.dennismuehlegger.gamelibrary.service;

import com.dennismuehlegger.gamelibrary.dto.LibraryDTO;
import com.dennismuehlegger.gamelibrary.dto.TransactionHistoryDTO;
import com.dennismuehlegger.gamelibrary.dto.TransactionItemDTO;
import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.entity.Library;
import com.dennismuehlegger.gamelibrary.entity.User;
import com.dennismuehlegger.gamelibrary.enums.TransactionResult;
import com.dennismuehlegger.gamelibrary.exception.*;
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
    public void buyGame(Long userId, Long gameId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found: " + gameId));

        boolean alreadyOwned = user.getLibraries().stream()
                .anyMatch(l -> l.getGame().getId().equals(game.getId()));

        if (alreadyOwned) {
            throw new GameAlreadyOwnedException("You already own " + game.getName() + "!");
        }

        if (user.getFunds().compareTo(game.getPrice()) < 0) {
            throw new InsufficientFundsException("You do not enough funds to buy " + game.getName() + "!");
        }

        Library library = new Library();
        library.setGame(game);
        library.setUser(user);

        user.getLibraries().add(library);
        user.setFunds(user.getFunds().subtract(game.getPrice()));

        userRepository.save(user);
    }

    public List<Game> getUserLibrary(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getLibraries().isEmpty()){
                return user.getLibraries().stream().map(Library::getGame).toList();
            }
        }
        return List.of();
    }

    public List<LibraryDTO> mapToLibraryDTO(Long userId, List<Game> games) {
        return games.stream()
                .map(game -> new LibraryDTO(
                        game.getId(),
                        game.getName(),
                        game.getPrice(),
                        game.getReleaseYear(),
                        game.getCoverArtUrl(),
                        userRepository.findById(userId).get().getLibraries().stream()
                                .filter(library -> library.getGame().getId().equals(game.getId()))
                                .findFirst()
                                .map(Library::getHoursPlayed)
                                .orElse(0)
                ))
                .toList();
    }

    public void playGame(Long userId, Long gameId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found: " + gameId));

        Library libraryGame = user.getLibraries().stream()
                .filter(l -> l.getGame().getId().equals(game.getId()))
                .findFirst()
                .orElse(null);

        if (libraryGame != null) {
            libraryGame.setHoursPlayed(libraryGame.getHoursPlayed() + 1);
            userRepository.save(user);
        } else {
            throw new GameNotOwnedException("You do not own " + game.getName()+ "!");
        }
    }

    public TransactionHistoryDTO getTransactionHistory(Long userId) {
        TransactionHistoryDTO transactionHistoryDTO = new TransactionHistoryDTO();
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getLibraries().isEmpty()){
                List<TransactionItemDTO> items = user.getLibraries().stream()
                        .map(library -> new TransactionItemDTO(
                                library.getGame().getName(),
                                library.getGame().getPrice()
                        ))
                        .toList();

                transactionHistoryDTO.setResult(TransactionResult.SUCCESS);
                transactionHistoryDTO.setTransactions(items);
                return transactionHistoryDTO;

            }
            transactionHistoryDTO.setResult(TransactionResult.NO_HISTORY);
            return transactionHistoryDTO;
        }
        transactionHistoryDTO.setResult(TransactionResult.NO_USER);
        return null;
    }
}
