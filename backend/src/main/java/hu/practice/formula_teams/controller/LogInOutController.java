package hu.practice.formula_teams.controller;

import hu.practice.formula_teams.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogInOutController {

    private final LoginService loginService;

    public LogInOutController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return loginService.login(loginRequestDTO);
    }

    @PostMapping("/doLogout")
    public void logout(HttpServletRequest request) {
        loginService.logout(request);
    }
}
