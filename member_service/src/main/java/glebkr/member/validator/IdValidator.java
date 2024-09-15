package glebkr.member.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import glebkr.member.annotation.IdConstraint;

public class IdValidator implements ConstraintValidator<IdConstraint, Integer> {

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("Validating value: " + integer);
        return integer != null && integer > 0;
    }

}
