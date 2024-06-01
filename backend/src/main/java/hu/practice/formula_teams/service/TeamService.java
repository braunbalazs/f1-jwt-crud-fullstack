package hu.practice.formula_teams.service;

import hu.practice.formula_teams.model.Team;
import hu.practice.formula_teams.controller.TeamDTO;

import java.util.List;

public interface TeamService {

    List<TeamDTO> getAllTeams();

    TeamDTO findTeamById(Integer id);

    TeamDTO createTeam(TeamDTO teamDTO);

    TeamDTO updateTeam(Integer id, TeamDTO teamDTO);

    void deleteTeam(Integer id);
}
