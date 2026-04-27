package com.dennismuehlegger.gamelibrary.repository;

import com.dennismuehlegger.gamelibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
