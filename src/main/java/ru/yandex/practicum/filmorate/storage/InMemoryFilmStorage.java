package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);

    private final Map<Long, Film> films = new HashMap<>();

    public Film add(Film film) {
        log.trace("Добавление фильма");
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    public Film update(Film film) {
        log.trace("Обновление фильма: {}", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    public void remove(long id) {
        log.trace("Удаление фильма.");
        films.remove(id);
    }

    public Optional<Film> get(long id) {
        log.trace("Получение фильма.");
        return Optional.ofNullable(films.get(id));
    }

    public Collection<Film> getAll() {
        log.info("Получение списка всех фильмов.");
        return films.values();
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }


}
