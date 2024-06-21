package hu.practice.formula_teams.service;

import hu.practice.formula_teams.controller.TeamDTO;
import hu.practice.formula_teams.controller.TeamMapper;
import hu.practice.formula_teams.exception.DuplicateException;
import hu.practice.formula_teams.model.Team;
import hu.practice.formula_teams.model.TeamRepository;
import hu.practice.formula_teams.validation.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TeamServiceTest {
    // Service to test
    @InjectMocks
    private TeamServiceImpl teamService;

    // Dependencies
    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMapper teamMapper;

    @Mock
    private ObjectsValidator<TeamDTO> objectsValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public  void should_successfully_save_team() {
        // Given
        TeamDTO requestDto = new TeamDTO(20, "TestTeam", 2022, 1, false);
        Team teamToSave = new Team("TestTeam", 2022, 1, false);
        Team teamSaved = new Team("TestTeam", 2022, 1, false);
        teamSaved.setId(20);

        when(objectsValidator.validate(requestDto)).thenReturn(Collections.emptySet());
        when(teamRepository.existsTeamByName(requestDto.name())).thenReturn(false);
        when(teamMapper.toEntity(requestDto)).thenReturn(teamToSave);
        when(teamRepository.save(teamToSave)).thenReturn(teamSaved);
        when(teamMapper.toDto(teamSaved)).thenReturn(new TeamDTO(20, "TestTeam", 2022, 1, false));

        // When
        TeamDTO responseDto = teamService.createTeam(requestDto);

        // Then
        assertEquals(requestDto.name(), responseDto.name());
        assertEquals(requestDto.yearEstablished(), responseDto.yearEstablished());
        assertEquals(requestDto.championshipsWon(), responseDto.championshipsWon());
        assertEquals(requestDto.regFeePaid(), responseDto.regFeePaid());
    }

    @Test
    public  void should_throw_when_saving_existing_team() {
        // Given
        TeamDTO requestDto = new TeamDTO(20, "TestTeam", 2022, 1, false);
        Team teamToSave = new Team("TestTeam", 2022, 1, false);
        Team teamSaved = new Team("TestTeam", 2022, 1, false);
        teamSaved.setId(20);

        when(objectsValidator.validate(requestDto)).thenReturn(Collections.emptySet());
        when(teamRepository.existsTeamByName(requestDto.name())).thenReturn(true);
        when(teamMapper.toEntity(requestDto)).thenReturn(teamToSave);
        when(teamRepository.save(teamToSave)).thenReturn(teamSaved);
        when(teamMapper.toDto(teamSaved)).thenReturn(new TeamDTO(20, "TestTeam", 2022, 1, false));

        // Then
        assertThrows(DuplicateException.class, () -> teamService.createTeam(requestDto));
    }

    @Test
    public void should_successfully_update_team() {
        //Given
        Integer idToUpdate = 20;
        TeamDTO requestDto = new TeamDTO(20, "TestTeam", 2022, 1, false);
        Team teamFoundInDb = new Team("OldTestTeam", 2020, 0, true);

        when(teamRepository.findById(idToUpdate)).thenReturn(Optional.of(teamFoundInDb));
        when(teamMapper.toDto(teamFoundInDb)).thenReturn(new TeamDTO(20, "TestTeam", 2022, 1, false));

        // When
        TeamDTO responseDto = teamService.updateTeam(idToUpdate, requestDto);

        // Then
        assertEquals(responseDto.id(), idToUpdate);
        assertEquals(responseDto.championshipsWon(), requestDto.championshipsWon());
        assertEquals(responseDto.yearEstablished(), requestDto.yearEstablished());
        assertEquals(responseDto.regFeePaid(), requestDto.regFeePaid());
    }

    @Test
    public void should_throw_when_updating_non_existing_team() {
        //Given
        Integer idToUpdate = 20;
        TeamDTO requestDto = new TeamDTO(20, "TestTeam", 2022, 1, false);
        Team teamFoundInDb = new Team("OldTestTeam", 2020, 0, true);

        when(teamRepository.findById(idToUpdate)).thenReturn(Optional.empty());
        when(teamMapper.toDto(teamFoundInDb)).thenReturn(new TeamDTO(20, "TestTeam", 2022, 1, false));

        // Then
        assertThrows(EntityNotFoundException.class, () -> teamService.updateTeam(idToUpdate, requestDto));
    }
}