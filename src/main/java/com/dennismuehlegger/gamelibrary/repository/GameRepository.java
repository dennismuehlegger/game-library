package com.dennismuehlegger.gamelibrary.repository;

import com.dennismuehlegger.gamelibrary.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
