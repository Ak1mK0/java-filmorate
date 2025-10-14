package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Получен список фильмов");
        return films.values();
    }

    @PostMapping
    public Film addNewFilm (@RequestBody Film film) {
        filmValidator(film);
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Фильм добавлен");
        return film;
    }

    @PutMapping
    public Film updateFilm (@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Фильм не найден");
            throw new ConditionsNotMetException("Такого фильма нет");
        }
        filmValidator(film);
        films.put(film.getId(), film);
        log.info("Фильм обновлен");
        return film;
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private void filmValidator(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Пустое название");
            throw new ConditionsNotMetException("Название не может быть пустым");
        }
        if (film.getDescription().length() >= 200) {
            log.warn("Длинное описание");
            throw new ConditionsNotMetException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,11,28))) {
            log.warn("Не корректная дата релиза");
            throw new ConditionsNotMetException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration().isNegative()) {
            log.warn("Отрицательная продолжительность фильма");
            throw new ConditionsNotMetException("Продолжительность фильма должна быть положительным числом");
        }
    }
}
