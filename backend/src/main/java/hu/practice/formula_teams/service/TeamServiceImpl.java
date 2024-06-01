package hu.practice.formula_teams.service;

import hu.practice.formula_teams.controller.TeamDTO;
import hu.practice.formula_teams.controller.TeamMapper;
import hu.practice.formula_teams.exception.DuplicateException;
import hu.practice.formula_teams.model.Team;
import hu.practice.formula_teams.model.TeamRepository;
import hu.practice.formula_teams.validation.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final ObjectsValidator<TeamDTO> objectsValidator;

    public TeamServiceImpl(TeamRepository teamRepository, TeamMapper teamMapper, ObjectsValidator<TeamDTO> objectsValidator) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.objectsValidator = objectsValidator;
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamMapper.toDtoList(teamRepository.findAll());
    }

    @Override
    public TeamDTO findTeamById(Integer id) {
        return teamMapper.toDto(teamRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Team with id: '%d' not found".formatted(id))));
    }

    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        objectsValidator.validate(teamDTO);
        if (teamRepository.existsTeamByName(teamDTO.name())) {
            throw new DuplicateException("Team by name: '%s' already exists".formatted(teamDTO.name()));
        }
        Team teamToSave = teamMapper.toEntity(teamDTO);
        Team teamSaved = teamRepository.save(teamToSave);
        return teamMapper.toDto(teamSaved);
    }

    @Override
    @Transactional
    public TeamDTO updateTeam(Integer id, TeamDTO teamDTO) {
        Team team = teamRepository.findById(id)
                                  .orElseThrow(() -> new EntityNotFoundException("Team with id: %d not found".formatted(teamDTO.id())));
        team.setName(teamDTO.name());
        team.setYearEstablished(teamDTO.yearEstablished());
        team.setChampionshipsWon(teamDTO.championshipsWon());
        team.setRegFeePaid(teamDTO.regFeePaid());
        return teamMapper.toDto(team);
    }

    @Override
    public void deleteTeam(Integer id) {
        teamRepository.deleteById(id);
    }
}
