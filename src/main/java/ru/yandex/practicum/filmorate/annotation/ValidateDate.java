package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validators.DateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateDate {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    BeforeOrAfter beforeOrAfter();

    String message();

    int year() default 0;

    int month() default 0;

    int day() default 0;


    enum BeforeOrAfter {
        isBefore,
        isAfter
    }
}
