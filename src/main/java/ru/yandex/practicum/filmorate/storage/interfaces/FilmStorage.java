package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    public Film add(Film film);

    public Optional<Film> get(long id);

    public Collection<Film> getAll();

    public Film update(Film film);

    public void remove(long id);
}
