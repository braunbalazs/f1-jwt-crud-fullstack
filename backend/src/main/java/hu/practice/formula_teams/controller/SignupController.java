package hu.practice.formula_teams.controller;

import hu.practice.formula_teams.service.SignupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService service) {
        this.signupService = service;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody SignUpDTO signUpDTO) {
        signupService.signup(signUpDTO);
    }
}
