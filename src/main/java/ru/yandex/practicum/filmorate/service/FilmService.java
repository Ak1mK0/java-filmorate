package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private static final Logger log = LoggerFactory.getLogger(FilmService.class);
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addNewFilm(Film film) {
        log.trace("Film addNewFilm command");
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        log.trace("Film updateFilm command");
        if (filmStorage.get(film.getId()).isEmpty()) {
            throw new ObjectNotFindException("Фильм с именем: " + film.getName() + " не существует");
        }
        return filmStorage.update(film);
    }

    public void removeFilm(Film film) {
        log.trace("Film removeFilm command");
        filmStorage.remove(film.getId());
    }

    public Film findFilmById(Film film) {
        log.trace("Film findFilmById command");
        Optional<Film> optionalFilm = filmStorage.get(film.getId());
        if (optionalFilm.isEmpty()) {
            throw new ObjectNotFindException("Фильм с именем: " + film.getName() + " не существует");
        }
        return optionalFilm.get();
    }

    public Collection<Film> findAll() {
        log.trace("Film findAll command");
        return filmStorage.getAll();
    }

    public Film addLike(long filmId, long userId) {
        log.trace("Film addLike command");
        if (userStorage.get(userId).isEmpty()) {
            throw new ObjectNotFindException("Пользователя с id: " + userId + " не существует");
        }
        Optional<Film> optionalFilm = filmStorage.get(filmId);
        if (optionalFilm.isEmpty()) {
            throw new ObjectNotFindException("Фильм с id: " + filmId + " не существует");
        }
        Film film = optionalFilm.get();
        Set<Long> filmLike = Optional.ofNullable(film.getLike())
                .orElse(new HashSet<>());
        filmLike.add(userId);
        film.setLike(filmLike);
        return film;
    }

    public Film removeLike(long filmId, long userId) {
        log.trace("Film removeLike command");
        if (userStorage.get(userId).isEmpty()) {
            throw new ObjectNotFindException("Пользователя с id: " + userId + " не существует");
        }
        Optional<Film> optionalFilm = filmStorage.get(filmId);
        if (optionalFilm.isEmpty()) {
            throw new ObjectNotFindException("Фильм с id: " + filmId + " не существует");
        }
        Film film = optionalFilm.get();
        Set<Long> filmLike = Optional.ofNullable(film.getLike())
                .orElse(new HashSet<>());
        filmLike.remove(userId);
        film.setLike(filmLike);
        return film;
    }

    public Collection<Film> getBestFilms(long count) {
        log.trace("Film getBestFilms command");
        return filmStorage.getAll()
                .stream()
                .sorted(Comparator
                        .comparingInt((Film film) -> Optional.ofNullable(film.getLike())
                                .map(Set::size).orElse(0))
                        .reversed()
                        .thenComparing(Film::getId))
                .limit(count)
                .collect(Collectors.toList());
    }
}
