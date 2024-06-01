package hu.practice.formula_teams.service;

import hu.practice.formula_teams.exception.DuplicateException;
import hu.practice.formula_teams.model.F1User;
import hu.practice.formula_teams.model.F1UserRepository;
import hu.practice.formula_teams.controller.SignUpDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupServiceImpl implements SignupService {

    private final F1UserRepository f1UserRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupServiceImpl(F1UserRepository f1UserRepository, PasswordEncoder passwordEncoder) {
        this.f1UserRepository = f1UserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signup(SignUpDTO signUpDTO) {
        // Check if user already exists
        String username = signUpDTO.username();
        if (f1UserRepository.existsF1UserByUsername(username)) {
            throw new DuplicateException("User with username: %s already exists".formatted(username));
        }

        // Save new user in db
        String hashedPassword = passwordEncoder.encode(signUpDTO.password());
        f1UserRepository.save(new F1User(signUpDTO.name(), username, hashedPassword));
    }
}
