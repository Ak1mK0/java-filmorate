package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.objectNotFindException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private static final Logger log = LoggerFactory.getLogger(FilmService.class);
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addNewFilm(Film film) {
        log.trace("---------Film addNewFilm command---------");
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        log.trace("---------Film updateFilm command---------");
        if (filmStorage.get(film.getId()).isEmpty()) {
            throw new objectNotFindException("Фильм с именем: " + film.getName() + " не существует");
        }
        return filmStorage.update(film);
    }

    public void removeFilm(Film film) {
        log.trace("---------Film removeFilm command---------");
        filmStorage.remove(film.getId());
    }

    public Film findFilmById(Film film) {
        log.trace("---------Film findFilmById command---------");
        Optional<Film> optionalFilm = filmStorage.get(film.getId());
        if (optionalFilm.isEmpty()) {
            throw new objectNotFindException("Фильм с именем: " + film.getName() + " не существует");
        }
        return optionalFilm.get();
    }

    public Collection<Film> findAll() {
        log.trace("---------Film findAll command---------");
        return filmStorage.getAll();
    }

    public Film addLike(long filmId, long userId) {
        log.trace("---------Film addLike command---------");
        if (userStorage.get(userId).isEmpty()) {
            throw new objectNotFindException("Пользователя с id: " + userId + " не существует");
        }
        Optional<Film> optionalFilm = filmStorage.get(filmId);
        if (optionalFilm.isEmpty()) {
            throw new objectNotFindException("Фильм с id: " + filmId + " не существует");
        }
        Film film = optionalFilm.get();
        Set<Long> filmLike = film.getLike();
        filmLike.add(userId);
        return film;
    }

    public Film removeLike(long filmId, long userId) {
        log.trace("---------Film removeLike command---------");
        if (userStorage.get(userId).isEmpty()) {
            throw new objectNotFindException("Пользователя с id: " + userId + " не существует");
        }
        Optional<Film> optionalFilm = filmStorage.get(filmId);
        if (optionalFilm.isEmpty()) {
            throw new objectNotFindException("Фильм с id: " + filmId + " не существует");
        }
        Film film = optionalFilm.get();
        Set<Long> filmLike = film.getLike();
        filmLike.remove(userId);
        return film;
    }

    public Collection<Film> getBestFilms(long count) {
        log.trace("---------Film getBestFilms command---------");
        return filmStorage.getAll()
                .stream()
                .sorted((o1, o2) -> o2.getLike().size() - o1.getLike().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
