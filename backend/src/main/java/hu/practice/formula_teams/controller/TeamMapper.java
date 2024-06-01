package hu.practice.formula_teams.controller;

import hu.practice.formula_teams.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    Team toEntity(TeamDTO teamDTO);

    TeamDTO toDto(Team team);

    List<Team> toEntityList(List<TeamDTO> teamDTOS);

    List<TeamDTO> toDtoList(List<Team> teams);
}
