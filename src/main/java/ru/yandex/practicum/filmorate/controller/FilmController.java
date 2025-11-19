package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmResponse;
import ru.yandex.practicum.filmorate.service.FilmService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewFilmResponse createFilm(@Valid @RequestBody FilmDto filmRequest) {
        log.debug("--Новый запрос createFilm--");
        log.debug("Film data: {}", filmRequest);
        return filmService.createFilm(filmRequest);
    }
}
