package hu.practice.formula_teams.controller;

import hu.practice.formula_teams.model.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TeamMapperTest {

    private final TeamMapper mapper = new TeamMapperImpl();

    @Test
    public void shouldMapTeamDTOToTeam() {
        TeamDTO dto = new TeamDTO(null, "TestTeam", 2023, 0, false);

        Team team = mapper.toEntity(dto);

        assertEquals(dto.name(), team.getName());
        assertEquals(dto.yearEstablished(), team.getYearEstablished());
        assertEquals(dto.championshipsWon(), team.getChampionshipsWon());
        assertNull(team.getId());
    }

    @Test
    public void should_return_null_when_studentDto_is_null() {
        Team team = mapper.toEntity(null);
        assertNull(team);
    }

    @Test
    public void shouldMapTeamToTeamDTO() {
        Team team = new Team("TestTeam", 2022, 1, true);

        TeamDTO dto = mapper.toDto(team);

        assertEquals(team.getName(), dto.name());
        assertEquals(team.getYearEstablished(), dto.yearEstablished());
        assertEquals(team.getChampionshipsWon(), dto.championshipsWon());
        assertNull(dto.id());
    }
}