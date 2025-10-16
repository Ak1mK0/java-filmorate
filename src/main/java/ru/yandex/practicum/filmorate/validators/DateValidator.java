package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotation.ValidateDate;
import ru.yandex.practicum.filmorate.annotation.ValidateDate.BeforeOrAfter;

import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<ValidateDate, LocalDate> {

    private BeforeOrAfter beforeOrAfter;
    private int year;
    private int month;
    private int day;
    private String message;

    @Override
    public void initialize(ValidateDate validateDate) {
        this.beforeOrAfter = validateDate.beforeOrAfter();
        this.message = validateDate.message();
        this.year = (validateDate.year() == 0) ? LocalDate.now().getYear() : validateDate.year();
        this.month = (validateDate.month() == 0) ? LocalDate.now().getMonthValue() : validateDate.month();
        this.day = (validateDate.day() == 0) ? LocalDate.now().getDayOfMonth() : validateDate.day();
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return false;
        }
        LocalDate targetDate = LocalDate.of(year, month, day);
        if (beforeOrAfter == BeforeOrAfter.isAfter && date.isAfter(targetDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        } else if (beforeOrAfter == BeforeOrAfter.isBefore && date.isBefore(targetDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
