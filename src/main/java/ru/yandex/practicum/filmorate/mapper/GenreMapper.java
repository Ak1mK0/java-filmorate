package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenreMapper {
    private static final Logger log = LoggerFactory.getLogger(GenreMapper.class);

    public static Genre mapToGenre(GenreDto genreDto) {
        Genre g = new Genre();
        g.setId(genreDto.getId());
        g.setName(genreDto.getName());
        return g;
    }

    public static Genre mapToGenre(FilmGenre filmGenre) {
        Genre g = new Genre();
        g.setId(filmGenre.getGenreId());
        g.setName(filmGenre.getGenreName());
        return g;
    }

    public static GenreDto mapToDto(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }
}
