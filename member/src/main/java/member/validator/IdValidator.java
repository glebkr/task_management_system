package member.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import member.annotation.IdConstraint;

public class IdValidator implements ConstraintValidator<IdConstraint, Integer> {
    @Override
    public void initialize(IdConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("Validating value: " + integer);
        return integer != null && integer > 0;
    }
}
