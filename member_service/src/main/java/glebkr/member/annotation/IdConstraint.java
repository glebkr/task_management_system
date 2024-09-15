package glebkr.member.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import glebkr.member.validator.IdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdValidator.class)
@Documented
public @interface IdConstraint {
    String message() default "Must be a positive integer";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
