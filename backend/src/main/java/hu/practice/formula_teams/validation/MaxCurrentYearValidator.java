package hu.practice.formula_teams.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Annotation;
import java.time.Year;

public class MaxCurrentYearValidator implements ConstraintValidator<MaxCurrentYear, Integer> {

    @Override
    public void initialize(MaxCurrentYear constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value==null) {
            return true;
        }
        return value <= Year.now().getValue();
    }
}
