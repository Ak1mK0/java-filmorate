package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.dal.RatingRepository;
import ru.yandex.practicum.filmorate.dto.*;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private static final Logger log = LoggerFactory.getLogger(FilmService.class);
    private final FilmRepository filmRepository;
    private final RatingRepository ratingRepository;
    private final GenreRepository genreRepository;


    public FilmService(FilmRepository filmRepository,
                       RatingRepository ratingRepository,
                       GenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.ratingRepository = ratingRepository;
        this.genreRepository = genreRepository;
    }

    public FilmResponse createFilm(FilmDto filmRequest) {


        if (ratingRepository.findById(filmRequest.getMpa().getId()).isEmpty()) {
            throw new ObjectNotFindException("Рейтинга с ID: " + filmRequest.getMpa().getId() + " не существует");
        }
        Set<Integer> ids = Optional.ofNullable(filmRequest.getGenres())
                .orElse(Collections.emptySet())
                .stream()
                .map(GenreDto::getId)
                .collect(Collectors.toSet());
        if (genreRepository.countExist(ids) != ids.size()) {
            throw new ObjectNotFindException("Указан не существующий жанр");
        }
        Film film = FilmMapper.mapToFilm(filmRequest);
        filmRepository.save(film);


        return FilmMapper.mapToFilmResponse(film);
    }

    public FilmResponse getFilm(long id) {
        Optional<Film> f = filmRepository.getFilm(id);
        if (f.isEmpty()) {
            throw new ObjectNotFindException("Фильм с ID =" + id + " не существует");
        }
        log.debug("Фильм: {}", f.get());
        return FilmMapper.mapToFilmResponse(f.get());
    }

    public Set<UserResponse> getAll() {
        return Collections.emptySet();
    }
}
