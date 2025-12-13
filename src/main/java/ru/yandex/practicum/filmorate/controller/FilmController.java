package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmResponse;
import ru.yandex.practicum.filmorate.dto.LikeResponse;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmResponse createFilm(@RequestBody @Valid FilmDto filmRequest) {
        log.debug("--Новый запрос createFilm--");
        log.debug("Film data: {}", filmRequest);
        return filmService.createFilm(filmRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public FilmResponse updateFilm(@RequestBody @Valid FilmDto filmRequest) {
        log.debug("--Новый запрос updateFilm--");
        log.debug("Film data: {}", filmRequest);
        return filmService.updateFilm(filmRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmResponse getFilm(@PathVariable long id) {
        log.debug("--Новый запрос getFilm--");
        log.debug("Film id: {}", id);
        return filmService.getFilm(id);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<FilmResponse> getPopular(@RequestParam(defaultValue = "10") long count) {
        log.debug("--Новый запрос getPopular--");
        log.debug("count: {}", count);
        if (count < 0) {
            throw new ObjectNotFindException("Параметр count должен быть больше 0");
        }
        return filmService.getPopular(count);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FilmResponse> getAll() {
        log.debug("--Новый запрос getAll--");
        return filmService.getAll();
    }

    @PutMapping("/{filmId}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public LikeResponse addLike(@PathVariable long filmId, @PathVariable long userId) {
        log.debug("--Новый запрос addLike--");
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean removeLike(@PathVariable long filmId, @PathVariable long userId) {
        log.debug("--Новый запрос removeLike--");
        return filmService.removeLike(filmId, userId);
    }
}
