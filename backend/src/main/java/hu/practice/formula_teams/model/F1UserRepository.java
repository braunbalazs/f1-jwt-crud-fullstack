package hu.practice.formula_teams.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface F1UserRepository extends JpaRepository<F1User, Integer> {
    Optional<F1User> findByUsername(String username);

    boolean existsF1UserByUsername(String username);
}
