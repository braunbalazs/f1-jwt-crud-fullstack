package hu.practice.formula_teams.controller;

import hu.practice.formula_teams.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService, TeamMapper teamMapper) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamDTO> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping("new")
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody TeamDTO teamDTO) {
        return ResponseEntity.status(201).body(teamService.createTeam(teamDTO));
    }

    @GetMapping("{id}")
    public TeamDTO findTeam(@PathVariable Integer id) {
        return teamService.findTeamById(id);
    }

    @PutMapping("{id}")
    public TeamDTO updateTeam(@PathVariable Integer id, @RequestBody TeamDTO teamDTO) {
        return teamService.updateTeam(id, teamDTO);
    }

    @DeleteMapping("{id}")
    public void deleteTeam(@PathVariable Integer id) {
        teamService.deleteTeam(id);
    }
}
