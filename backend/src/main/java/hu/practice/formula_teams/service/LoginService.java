package hu.practice.formula_teams.service;

import hu.practice.formula_teams.controller.LoginRequestDTO;
import hu.practice.formula_teams.controller.LoginResponseDTO;

public interface LoginService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
