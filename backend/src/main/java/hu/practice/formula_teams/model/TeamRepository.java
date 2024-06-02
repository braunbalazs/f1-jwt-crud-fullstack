package hu.practice.formula_teams.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    boolean existsTeamByName(String name);

    @Query("SELECT t FROM Team t ORDER BY t.name")
    List<Team> findAllOrderByName();
}
