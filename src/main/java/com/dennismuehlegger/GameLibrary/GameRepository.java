package com.dennismuehlegger.GameLibrary;

import com.dennismuehlegger.Entitites.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
