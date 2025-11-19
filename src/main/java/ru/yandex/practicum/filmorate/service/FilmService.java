package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.RatingRepository;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmResponse;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;

@Service
public class FilmService {
    private static final Logger log = LoggerFactory.getLogger(FilmService.class);
    private final FilmRepository filmRepository;
    private final RatingRepository ratingRepository;

    public FilmService(FilmRepository filmRepository, RatingRepository ratingRepository) {
        this.filmRepository = filmRepository;
        this.ratingRepository = ratingRepository;
    }

    public NewFilmResponse createFilm(FilmDto filmRequest) {
        if (ratingRepository.findById(filmRequest.getMpa().getId()).isEmpty()) {
            throw new ObjectNotFindException("Рейтинга с ID: " + filmRequest.getMpa().getId() + " не существует");
        }

        Film film = FilmMapper.mapToFilm(filmRequest);
        filmRepository.save(film);
        return FilmMapper.mapToNewFilmResponse(film);
    }
}
