package com.dennismuehlegger.gamelibrary.service;

import com.dennismuehlegger.gamelibrary.entity.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @InjectMocks
    GameService gameService;

    @Test
    public void testFilterGame_ByExactPrice(){
        // arrange
        List<Game> games = new ArrayList<>();

        Game game = new Game();
        game.setPrice(BigDecimal.valueOf(13.99));
        Game game1 = new Game();
        game1.setPrice(BigDecimal.valueOf(19.99));

        games.add(game);
        games.add(game1);

        // act
        List<Game> filteredGames = gameService.filterGames(games, null, null, null, BigDecimal.valueOf(19.99), null);

        // assert
        assertEquals(BigDecimal.valueOf(19.99), filteredGames.get(0).getPrice());
        assertEquals(1, filteredGames.size());
    }

    @Test
    public void testFilterGame_ByMaxPriceRange(){
        // arrange
        List<Game> games = new ArrayList<>();

        Game game = new Game();
        game.setPrice(BigDecimal.valueOf(13.99));
        Game game1 = new Game();
        game1.setPrice(BigDecimal.valueOf(15.99));
        Game game2 = new Game();
        game2.setPrice(BigDecimal.valueOf(19.99));

        games.add(game);
        games.add(game1);
        games.add(game2);

        // act
        List<Game> filteredGames = gameService.filterGames(games, null, null, BigDecimal.valueOf(15.99), null, null);

        // assert
        assertEquals(2, filteredGames.size());
    }

    @Test
    public void testFilterGame_ByName(){
        // arrange
        List<Game> games = new ArrayList<>();

        Game game = new Game();
        game.setName("test");
        Game game1 = new Game();
        game1.setName("test2");

        games.add(game);
        games.add(game1);

        // act
        List<Game> filteredGames = gameService.filterGames(games, null, null, null, null, "test");

        // assert
        assertEquals(1, filteredGames.size());
    }

    @Test
    public void testFilterGame_NoMatch(){
        // arrange
        List<Game> games = new ArrayList<>();

        Game game = new Game();
        game.setPrice(BigDecimal.valueOf(13.99));

        games.add(game);

        // act
        List<Game> filteredGames = gameService.filterGames(games, null, null, null, BigDecimal.valueOf(19.99), null);

        // assert
        assertEquals(0, filteredGames.size());
    }

    @Test
    public void testSortGame_ByPrice(){
        // arrange
        List<Game> games = new ArrayList<>();

        Game game = new Game();
        game.setPrice(BigDecimal.valueOf(13.99));
        Game game1 = new Game();
        game1.setPrice(BigDecimal.valueOf(19.99));
        Game game2 = new Game();
        game2.setPrice(BigDecimal.valueOf(15.99));

        games.add(game);
        games.add(game1);
        games.add(game2);

        // act
        List<Game> filteredGames = gameService.sortGames(games, null, Boolean.TRUE, null, null);

        // assert
        assertEquals(BigDecimal.valueOf(19.99), filteredGames.get(2).getPrice());
    }

    @Test
    public void testSortGame_ByPriceDescending(){
        // arrange
        List<Game> games = new ArrayList<>();

        Game game = new Game();
        game.setPrice(BigDecimal.valueOf(13.99));
        Game game1 = new Game();
        game1.setPrice(BigDecimal.valueOf(19.99));
        Game game2 = new Game();
        game2.setPrice(BigDecimal.valueOf(15.99));

        games.add(game);
        games.add(game1);
        games.add(game2);

        // act
        List<Game> filteredGames = gameService.sortGames(games, null, Boolean.TRUE, null, Boolean.TRUE);

        // assert
        assertEquals(BigDecimal.valueOf(13.99), filteredGames.get(2).getPrice());
    }
}
