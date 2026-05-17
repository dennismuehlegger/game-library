package com.dennismuehlegger.gamelibrary.service;

import com.dennismuehlegger.gamelibrary.dto.TransactionHistoryDTO;
import com.dennismuehlegger.gamelibrary.entity.Game;
import com.dennismuehlegger.gamelibrary.entity.Library;
import com.dennismuehlegger.gamelibrary.entity.User;
import com.dennismuehlegger.gamelibrary.enums.TransactionResult;
import com.dennismuehlegger.gamelibrary.exception.*;
import com.dennismuehlegger.gamelibrary.repository.GameRepository;
import com.dennismuehlegger.gamelibrary.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void testBuyGame_Success(){
        // arrange
        List<Library> libraries = new ArrayList<>();

        User user = new User();
        user.setFunds(BigDecimal.valueOf(100.0));
        user.setLibraries(libraries);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Game game = new Game();
        game.setPrice(BigDecimal.valueOf(50.0));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // act
        userService.buyGame(1L, 1L);

        // assert
        assertEquals(BigDecimal.valueOf(50.0), user.getFunds());
        assertEquals(1, user.getLibraries().size());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testBuyGame_InsufficientFunds(){
        // arrange
        List<Library> libraries = new ArrayList<>();

        User user = new User();
        user.setFunds(BigDecimal.valueOf(49.99));
        user.setLibraries(libraries);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Game game = new Game();
        game.setPrice(BigDecimal.valueOf(50.0));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // assert
        assertThrows(InsufficientFundsException.class, () -> { userService.buyGame(1L, 1L);});
    }

    @Test
    public void testBuyGame_GameAlreadyOwned(){
        // arrange
        Library library = new Library();
        List<Library> libraries = new ArrayList<>();

        User user = new User();
        user.setFunds(BigDecimal.valueOf(49.99));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Game game = new Game();
        game.setId(1L);
        game.setPrice(BigDecimal.valueOf(50.0));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        library.setUser(user);
        library.setGame(game);
        libraries.add(library);
        user.setLibraries(libraries);

        // assert
        assertThrows(GameAlreadyOwnedException.class, () -> { userService.buyGame(1L, 1L);});
    }

    @Test
    public void testBuyGame_UserNotFound(){
        // assert
        assertThrows(UserNotFoundException.class, () -> { userService.buyGame(1L, 1L);});
    }

    @Test
    public void testBuyGame_GameNotFound(){
        // arrange
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // assert
        assertThrows(GameNotFoundException.class, () -> { userService.buyGame(1L, 1L);});
    }

    @Test
    public void testPlayGame_Success(){
        // arrange
        Library library = new Library();
        List<Library> libraries = new ArrayList<>();

        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Game game = new Game();
        game.setId(1L);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        library.setUser(user);
        library.setGame(game);
        libraries.add(library);
        user.setLibraries(libraries);

        // act
        userService.playGame(1L, 1L);

        // assert
        assertEquals(1, library.getHoursPlayed());
        assertEquals(1, user.getLibraries().size());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testPlayGame_GameNotOwned(){
        // arrange
        Library library = new Library();
        List<Library> libraries = new ArrayList<>();

        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Game game = new Game();
        game.setId(1L);
        Game notOwnedGame = new Game();
        game.setId(2L);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(notOwnedGame));

        library.setUser(user);
        library.setGame(game);
        libraries.add(library);
        user.setLibraries(libraries);

        // assert
        assertThrows(GameNotOwnedException.class, () -> { userService.playGame(1L, 1L);});
    }

    @Test
    public void testPlayGame_UserNotFound(){
        // assert
        assertThrows(UserNotFoundException.class, () -> { userService.playGame(1L, 1L);});
    }

    @Test
    public void testPlayGame_GameNotFound(){
        // arrange
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // assert
        assertThrows(GameNotFoundException.class, () -> { userService.playGame(1L, 1L);});
    }

    @Test
    public void testGetTransactionHistory_Success(){
        // arrange
        Library library = new Library();
        List<Library> libraries = new ArrayList<>();

        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Game game = new Game();
        game.setName("test game");

        library.setUser(user);
        library.setGame(game);
        libraries.add(library);
        user.setLibraries(libraries);


        // act
        TransactionHistoryDTO result = userService.getTransactionHistory(1L);

        // assert
        assertEquals(TransactionResult.SUCCESS, result.getResult());
        assertEquals(1, result.getTransactions().size());
        assertEquals("test game", result.getTransactions().get(0).getGameName());
    }

    @Test
    public void testGetTransactionHistory_NoHistory(){
        // arrange
        List<Library> libraries = new ArrayList<>();

        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        user.setLibraries(libraries);

        // act
        TransactionHistoryDTO result = userService.getTransactionHistory(1L);

        // assert
        assertEquals(TransactionResult.NO_HISTORY, result.getResult());
    }

    @Test
    public void testGetTransactionHistory_NoUser(){
        // act
        TransactionHistoryDTO result = userService.getTransactionHistory(1L);

        // assert
        assertEquals(TransactionResult.NO_USER, result.getResult());
    }

}
