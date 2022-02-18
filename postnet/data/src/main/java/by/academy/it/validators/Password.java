package by.academy.it.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Пароль не соответствует заданным условиям";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
