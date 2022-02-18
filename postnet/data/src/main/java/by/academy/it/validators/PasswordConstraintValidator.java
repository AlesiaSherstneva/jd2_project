package by.academy.it.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

    String upperPattern = ".*[A-ZА-ЯЁ].*";
    String lowerPattern = ".*[a-zа-яё].*";
    String lengthPattern = ".{8}.*";
    String digitPattern = ".*[0-9].*";

    @Override
    public void initialize(Password passwordAnnotation) {

    }

    @Override
    public boolean isValid(String parameter, ConstraintValidatorContext context) {
        try {
            return parameter.matches(upperPattern) & parameter.matches(lowerPattern)
                    & parameter.matches(lengthPattern) & parameter.matches(digitPattern);
        } catch (NullPointerException exception) {
            return true;
        }
    }
}
