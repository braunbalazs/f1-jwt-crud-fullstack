package hu.practice.formula_teams.controller;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "'username' is required and cannot be blank")
        String username,
        @NotBlank(message = "'password' is required and cannot be blank")
        String password) {
}
