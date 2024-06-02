package hu.practice.formula_teams.service;

import hu.practice.formula_teams.controller.LoginRequestDTO;
import hu.practice.formula_teams.controller.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    void logout(HttpServletRequest request);
}
