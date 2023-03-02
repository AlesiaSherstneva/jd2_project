package by.academy.it.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {
    private final static String UPPER_PATTERN = ".*[A-ZА-ЯЁ].*";
    private final static String LOWER_PATTERN = ".*[a-zа-яё].*";
    private final static String DIGIT_PATTERN = ".*[0-9].*";
    private final static String LENGTH_PATTERN = ".{8}.*";

    @Override
    public void initialize(Password passwordAnnotation) {}

    @Override
    public boolean isValid(String parameter, ConstraintValidatorContext context) {
        if (parameter == null) return true;
        return parameter.matches(UPPER_PATTERN) & parameter.matches(LOWER_PATTERN)
                    & parameter.matches(DIGIT_PATTERN) & parameter.matches(LENGTH_PATTERN);
    }
}