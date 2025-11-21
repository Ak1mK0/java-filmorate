package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmResponse;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FilmMapper {
    private static final Logger log = LoggerFactory.getLogger(FilmMapper.class);

    public static Film mapToFilm(FilmDto request) {
        Film f = new Film();
        f.setId(request.getId());
        f.setName(request.getName());
        f.setDescription(request.getDescription());
        f.setReleaseDate(request.getReleaseDate());
        f.setDuration(request.getDuration());

        Optional<MpaDto> mpaDto = Optional.ofNullable(request.getMpa());
        if (mpaDto.isEmpty()) {
            f.setMpa(new Mpa());
        } else {
            f.setMpa(MpaMapper.mapToMpa(mpaDto.get()));
        }

        Set<Genre> genre = Optional.ofNullable(request.getGenres())
                .orElse(new HashSet<>())
                .stream()
                .map(GenreMapper::mapToGenre)
                .collect(Collectors.toSet());
        f.setGenres(genre);
        return f;
    }

    public static FilmResponse mapToFilmResponse(Film film) {
        FilmResponse dto = new FilmResponse();
        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setDuration(film.getDuration());
        dto.setGenres(film.getGenres().stream()
                .map(GenreMapper::mapToDto)
                .collect(Collectors.toSet())
        );
        dto.setMpa(MpaMapper.mapToDto(film.getMpa()));
        return dto;
    }
}

