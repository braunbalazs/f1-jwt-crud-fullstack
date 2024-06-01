package hu.practice.formula_teams.auth;

import hu.practice.formula_teams.model.F1User;
import hu.practice.formula_teams.model.F1UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final F1UserRepository f1UserRepository;

    public UserDetailsServiceImpl(F1UserRepository f1UserRepository) {
        this.f1UserRepository = f1UserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        F1User f1User = f1UserRepository.findByUsername(username)
                                        .orElseThrow(() -> new UsernameNotFoundException("User with username: %s not found.".formatted(username)));
        return User.builder()
                   .username(f1User.getUsername())
                   .password(f1User.getPassword())
                   .build();
    }
}
