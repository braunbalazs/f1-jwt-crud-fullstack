package hu.practice.formula_teams.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String errorMessage, HttpStatus status) {
}
