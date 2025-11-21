package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.*;
import ru.yandex.practicum.filmorate.dto.*;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.LikeMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private static final Logger log = LoggerFactory.getLogger(FilmService.class);
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final FilmGenresRepository filmGenresRepository;
    private final GenreRepository genreRepository;
    private final LikeRepository likeRepository;


    public FilmService(FilmRepository filmRepository, UserRepository userRepository,
                       RatingRepository ratingRepository, FilmGenresRepository filmGenresRepository,
                       GenreRepository genreRepository, LikeRepository likeRepository) {
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.filmGenresRepository = filmGenresRepository;
        this.genreRepository = genreRepository;
        this.likeRepository = likeRepository;
    }

    public FilmResponse createFilm(FilmDto filmRequest) {
        filmValidator(filmRequest);

        Film film = FilmMapper.mapToFilm(filmRequest);
        film = filmRepository.save(film);
        return FilmMapper.mapToFilmResponse(film);
    }

    public FilmResponse updateFilm(FilmDto filmRequest) {
        filmValidator(filmRequest);
        Film film = FilmMapper.mapToFilm(filmRequest);
        filmRepository.update(film);
        return FilmMapper.mapToFilmResponse(film);
    }

    public FilmResponse getFilm(long id) {
        Optional<Film> optFilm = filmRepository.getFilm(id);
        if (optFilm.isEmpty()) {
            throw new ObjectNotFindException("Фильм с ID =" + id + " не существует");
        }

        List<Genre> genres = filmGenresRepository.findFilmGenres(id);
        Film film = optFilm.get();
        film.setGenres(new HashSet<>(genres));

        return FilmMapper.mapToFilmResponse(film);
    }

    public List<FilmResponse> getAll() {
        return filmRepository.getAll().stream()
                .peek(film -> {
                    List<Genre> genres = filmGenresRepository.findFilmGenres(film.getId());
                    film.setGenres(new HashSet<>(genres));
                })
                .map(FilmMapper::mapToFilmResponse)
                .collect(Collectors.toList());
    }

    public List<FilmResponse> getPopular(long count) {
        return filmRepository.getPopular(count).stream()
                .peek(film -> {
                    List<Genre> genres = filmGenresRepository.findFilmGenres(film.getId());
                    film.setGenres(new HashSet<>(genres));
                })
                .map(FilmMapper::mapToFilmResponse)
                .collect(Collectors.toList());
    }

    public LikeResponse addLike(long filmId, long userId) {
        Optional<Film> film = filmRepository.getFilm(filmId);
        Optional<User> user = userRepository.findById(userId);

        if (film.isPresent() && user.isPresent() && likeRepository.findOne(filmId, userId).isEmpty()) {
            Like like = likeRepository.save(filmId, userId);
            return LikeMapper.mapToResponse(like);
        } else {
            throw new ObjectAlreadyExistException("Пользователь может поставить только 1 лайк");
        }
    }

    public boolean removeLike(long filmId, long userId) {
        Optional<Film> film = filmRepository.getFilm(filmId);
        Optional<User> user = userRepository.findById(userId);

        if (film.isPresent() && user.isPresent()) {
            return likeRepository.delete(filmId, userId);
        } else {
            throw new ObjectAlreadyExistException("Фильма или пользователя не существует");
        }
    }

    private void filmValidator(FilmDto filmRequest) {
        Optional<MpaDto> mpaDto = Optional.ofNullable(filmRequest.getMpa());
        if (mpaDto.isPresent() && ratingRepository.findById(filmRequest.getMpa().getId()).isEmpty()) {
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
    }
}
