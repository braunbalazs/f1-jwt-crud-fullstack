package hu.practice.formula_teams.service;

import hu.practice.formula_teams.auth.JwtService;
import hu.practice.formula_teams.controller.LoginRequestDTO;
import hu.practice.formula_teams.controller.LoginResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.username();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequestDTO.password()));
        String token = jwtService.generateToken(username);
        return new LoginResponseDTO(username, token);
    }
}
