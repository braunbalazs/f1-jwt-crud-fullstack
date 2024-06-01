package hu.practice.formula_teams.validation;

import java.util.Set;

public interface ObjectsValidator<T> {
    Set<String> validate(T objectToValidate);
}
