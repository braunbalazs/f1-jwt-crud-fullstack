package hu.practice.formula_teams.controller;

import hu.practice.formula_teams.validation.MaxCurrentYear;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;

public record TeamDTO(
        Integer id,
        @NotBlank(message = "'name' is required and cannot be blank")
        @Size(min = 3, max = 100,  message = "Length of 'name' must be between 3 and 100 characters")
        String name,
        @NotNull(message = "'yearEstablished' is required")
        @MaxCurrentYear(message = "'yearEstablished' cannot be greater than the current year")
        @Min(value = 0)
        Integer yearEstablished,
        @NotNull(message = "'championshipsWon' is required")
        Integer championshipsWon,
        @NotNull(message = "'regFeePaid' is required")
        Boolean regFeePaid) {
}
