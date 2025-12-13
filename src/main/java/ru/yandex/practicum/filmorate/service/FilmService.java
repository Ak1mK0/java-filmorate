package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.*;
import ru.yandex.practicum.filmorate.dto.*;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.mapper.LikeMapper;
import ru.yandex.practicum.filmorate.model.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
public class FilmService {
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
        filmGenresRepository.updateFilmGenre(film.getId(), film.getGenres());
        return FilmMapper.mapToFilmResponse(film);
    }

    public FilmResponse updateFilm(FilmDto filmRequest) {
        filmValidator(filmRequest);
        Film film = FilmMapper.mapToFilm(filmRequest);
        filmRepository.update(film);
        filmGenresRepository.updateFilmGenre(film.getId(), film.getGenres());
        return FilmMapper.mapToFilmResponse(film);
    }

    public FilmResponse getFilm(long id) {
        Optional<Film> optFilm = filmRepository.getFilm(id);
        if (optFilm.isEmpty()) {
            throw new ObjectNotFindException("Фильм с ID =" + id + " не существует");
        }

        List<FilmGenre> filmGenres = filmGenresRepository.findFilmGenres(List.of(id));
        Set<Genre> genres = filmGenres.stream()
                .map(GenreMapper::mapToGenre)
                .collect(Collectors.toSet());

        Film film = optFilm.get();
        film.setGenres(new HashSet<>(genres));

        return FilmMapper.mapToFilmResponse(film);
    }

    public List<FilmResponse> getAll() {
        List<Film> filmList = filmRepository.getAll();
        List<Long> ids = filmList.stream()
                .map(Film::getId)
                .toList();

        List<FilmGenre> filmGenres = filmGenresRepository.findFilmGenres(ids);
        Map<Long, List<FilmGenre>> genreGroupByFilmId = filmGenres.stream()
                .collect(Collectors.groupingBy((FilmGenre::getFilmId)));

        return filmList.stream()
                .peek(film -> {
                    if (genreGroupByFilmId.containsKey(film.getId())) {
                        Set<Genre> genres = genreGroupByFilmId.get(film.getId()).stream()
                                .map(GenreMapper::mapToGenre)
                                .collect(toSet());
                        film.setGenres(genres);
                    }
                })
                .map(FilmMapper::mapToFilmResponse)
                .toList();
    }


    public List<FilmResponse> getPopular(long count) {
        List<Film> filmList = filmRepository.getPopular(count);
        List<Long> ids = filmList.stream()
                .map(Film::getId)
                .toList();

        List<FilmGenre> filmGenres = filmGenresRepository.findFilmGenres(ids);
        Map<Long, List<FilmGenre>> genreGroupByFilmId = filmGenres.stream()
                .collect(Collectors.groupingBy((FilmGenre::getFilmId)));

        return filmList.stream()
                .peek(film -> {
                    if (genreGroupByFilmId.containsKey(film.getId())) {
                        Set<Genre> genres = genreGroupByFilmId.get(film.getId()).stream()
                                .map(GenreMapper::mapToGenre)
                                .collect(toSet());
                        film.setGenres(genres);
                    }
                })
                .map(FilmMapper::mapToFilmResponse)
                .toList();
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
