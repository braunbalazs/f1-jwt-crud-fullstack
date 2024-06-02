package hu.practice.formula_teams.auth;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistImpl implements TokenBlacklist {

    private final Set<String> blacklist = new HashSet<>();
    @Override
    public void addToBlacklist(String token) {
        blacklist.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
