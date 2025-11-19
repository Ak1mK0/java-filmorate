package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmResponse;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FilmMapper {
    private static final Logger log = LoggerFactory.getLogger(FilmMapper.class);

    public static Film mapToFilm(FilmDto request) {
        Film f = new Film();

        f.setName(request.getName());
        log.debug("new f: {}", f.getName());
        f.setDescription(request.getDescription());
        log.debug("setName f: {}", f.getDescription());
        f.setReleaseDate(request.getReleaseDate());
        log.debug("setReleaseDate f: {}", f.getReleaseDate());
        f.setDuration(request.getDuration());
        log.debug("setDuration f: {}", f.getDuration());
        f.setMpa(MpaMapper.mapToMpa(request.getMpa()));
        log.debug("setMpa f: {}", f.getMpa());
        f.setGenres(request.getGenres().stream()
                .map(GenreMapper::mapToGenre)
                .collect(Collectors.toSet()));
        log.debug("setGenres f: {}", f.getGenres());
        return f;
    }

    public static NewFilmResponse mapToNewFilmResponse(Film film) {
        NewFilmResponse dto = new NewFilmResponse();

        dto.setId(film.getId());
        log.debug("id dto: {}", dto.getId());
        dto.setName(film.getName());
        log.debug("setName dto: {}", dto.getName());
        dto.setDescription(film.getDescription());
        log.debug("setDescription dto: {}", dto.getDescription());
        dto.setReleaseDate(film.getReleaseDate());
        log.debug("setReleaseDate dto: {}", dto.getReleaseDate());
        dto.setDuration(film.getDuration());
        log.debug("setDuration dto: {}", dto.getDuration());
        dto.setMpa(MpaMapper.mapToDto(film.getMpa()));
        log.debug("setMpa dto: {}", dto.getMpa());
        dto.setGenres(film.getGenres().stream()
                .map(GenreMapper::mapToDto)
                .collect(Collectors.toSet())
        );
        log.debug("setGenres dto: {}", dto.getGenres());
        return dto;
    }
}

