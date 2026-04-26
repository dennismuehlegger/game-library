package com.dennismuehlegger.GameLibrary;

import com.dennismuehlegger.Entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
