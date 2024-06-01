package hu.practice.formula_teams.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    boolean existsTeamByName(String name);
}
