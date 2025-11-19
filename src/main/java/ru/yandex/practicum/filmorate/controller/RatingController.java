package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public final class RatingController {
    private static final Logger log = LoggerFactory.getLogger(RatingController.class);
    private final RatingService ratingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MpaDto> getRatings() {
        log.debug("--Новый запрос: getRatings--");
        return ratingService.getRatings();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MpaDto findRating(@PathVariable @Positive int id) {
        log.debug("--Новый запрос findRating--");
        return ratingService.findRating(id);
    }
}
