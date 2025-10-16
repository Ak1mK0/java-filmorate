package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotation.ValidateNoBlank;

public class NotBlankValidator implements ConstraintValidator<ValidateNoBlank, String> {

    @Override
    public boolean isValid(String text, ConstraintValidatorContext context) {
        if (text == null) {
            return true;
        }
        return !text.contains(" ");
    }
}
