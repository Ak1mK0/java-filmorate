package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.objectNotFindException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.trace("---------Film findAll command---------");
        return filmService.findAll();
    }

    @PostMapping
    public Film addNewFilm(@Valid @RequestBody Film film) {
        log.trace("---------Film addNewFilm command---------");
        return filmService.addNewFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.trace("---------Film updateFilm command---------");
        return filmService.updateFilm(film);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilm(@RequestParam(defaultValue = "10") long count) {
        log.trace("---------Film getPopularFilm command---------");
        if (count < 0) {
            throw new objectNotFindException("Параметр count должен быть больше 0");
        }
        return filmService.getBestFilms(count);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable long filmId, @PathVariable long userId) {
        log.trace("---------Film addLike command---------");
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable long filmId, @PathVariable long userId) {
        log.trace("---------Film removeLike command---------");
        return filmService.removeLike(filmId, userId);
    }

}

