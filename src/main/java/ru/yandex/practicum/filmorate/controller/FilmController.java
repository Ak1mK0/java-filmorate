package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmResponse;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collections;
import java.util.Set;

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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmResponse getFilm(@PathVariable long id) {
        log.debug("--Новый запрос getFilm--");
        log.debug("Film id: {}", id);
        return filmService.getFilm(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<FilmResponse> getAll() {
        log.debug("--Новый запрос getAll--");
        return Collections.emptySet();
    }
}
