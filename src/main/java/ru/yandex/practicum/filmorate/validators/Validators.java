package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validators {

    public static void validateFilm(Film film) {
        validateNotEmptyField(film.getName());
        validateDescription(film.getDescription());
        validateReleaseDate(film.getReleaseDate());
        validateDuration(film.getDuration());
    }

    public static void validateUser(User user) {
        validateNotEmptyField(user.getEmail());
        validateEmail(user.getEmail());
        validateNotEmptyField(user.getLogin());
        validateLogin(user.getLogin());
        validateBirthday(user.getBirthday());
    }

    private static void validateDescription(String description) {
        if (description != null && description.length() > 200) {
            throw new ConditionsNotMetException("Максимальная длина описания — 200 символов");
        }
    }

    private static void validateReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ConditionsNotMetException("Дата релиза — не раньше 28 декабря 1895 года");
        }
    }

    private static void validateDuration(Long duration) {
        if (duration <= 0) {
            throw new ConditionsNotMetException("Продолжительность фильма должна быть положительным числом");
        }
    }

    private static void validateNotEmptyField(String field) {
        if (field == null || field.isBlank()) {
            throw new ConditionsNotMetException("Поле не может быть пустым");
        }
    }

    private static void validateEmail(String email) {
        if (!email.contains("@")) {
            throw new ConditionsNotMetException("Не корректная электронная почта");
        }
    }

    private static void validateLogin(String login) {
        if (login.contains(" ")) {
            throw new ConditionsNotMetException("Поле не может содержать пробелы");
        }
    }

    private static void validateBirthday(LocalDate birthday) {
        if (birthday.isAfter(LocalDate.now())) {
            throw new ConditionsNotMetException("Дата рождения не может быть в будущем");
        }
    }
}