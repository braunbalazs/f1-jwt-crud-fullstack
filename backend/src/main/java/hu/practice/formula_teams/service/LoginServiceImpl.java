package hu.practice.formula_teams.service;

import hu.practice.formula_teams.auth.JwtService;
import hu.practice.formula_teams.auth.TokenBlacklist;
import hu.practice.formula_teams.controller.LoginRequestDTO;
import hu.practice.formula_teams.controller.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenBlacklist tokenBlacklist;

    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, TokenBlacklist tokenBlacklist) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.username();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequestDTO.password()));
        String token = jwtService.generateToken(username);
        return new LoginResponseDTO(username, token);
    }

    @Override
    public void logout(HttpServletRequest request) {
        String token = jwtService.extractToken(request);
        tokenBlacklist.addToBlacklist(token);
    }
}
