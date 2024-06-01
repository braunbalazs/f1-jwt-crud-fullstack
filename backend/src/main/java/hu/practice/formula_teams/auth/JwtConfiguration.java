package hu.practice.formula_teams.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt.token")
public class JwtConfiguration {
    private String secret;
    private Integer validityMinutes;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getValidityMinutes() {
        return validityMinutes;
    }

    public void setValidityMinutes(Integer validityMinutes) {
        this.validityMinutes = validityMinutes;
    }
}
