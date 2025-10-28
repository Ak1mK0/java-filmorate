package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
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
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        if (filmStorage.get(film.getId()).isEmpty()) {
            throw new ConditionsNotMetException("Фильм с именем: " + film.getName() + " не существует");
        }
        return filmStorage.update(film);
    }

    public void removeFilm(Film film) {
        filmStorage.remove(film.getId());
    }

    public Film findFilmById(Film film) {
        Optional<Film> optionalFilm = filmStorage.get(film.getId());
        if (optionalFilm.isEmpty()) {
            throw new ConditionsNotMetException("Фильм с именем: " + film.getName() + " не существует");
        }
        return optionalFilm.get();
    }

    public Collection<Film> findAll() {
        return filmStorage.getAll();
    }

    public void addLike(long filmId, long userId) {
        if (userStorage.get(userId).isEmpty()) {
            throw new ConditionsNotMetException("Пользователя с id: " + userId + " не существует");
        }
        Optional<Film> optionalFilm = filmStorage.get(filmId);
        if (optionalFilm.isEmpty()) {
            throw new ConditionsNotMetException("Фильм с id: " + filmId + " не существует");
        }
        Film film = optionalFilm.get();
        Set<Long> filmLike = film.getLike();
        filmLike.add(userId);
    }

    public void removeLike(long filmId, long userId) {
        if (userStorage.get(userId).isEmpty()) {
            throw new ConditionsNotMetException("Пользователя с id: " + userId + " не существует");
        }
        Optional<Film> optionalFilm = filmStorage.get(filmId);
        if (optionalFilm.isEmpty()) {
            throw new ConditionsNotMetException("Фильм с id: " + filmId + " не существует");
        }
        Film film = optionalFilm.get();
        Set<Long> filmLike = film.getLike();
        filmLike.remove(userId);
    }

    public Collection<Film> getBestFilms(long count) {
        return filmStorage.getAll()
                .stream()
                .sorted((o1, o2) -> o2.getLike().size() - o1.getLike().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
