package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.dto.*;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan("ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmServiceFilmoRateApplicationTests {
    private final FilmService filmService;

    @Test
    public void testFilmCreate() {
        FilmDto dto = FilmDto.builder()
                .name("Film5")
                .description("Description")
                .duration(90)
                .releaseDate(LocalDate.of(1998, 5, 24))
                .mpa(new MpaDto(2, null))
                .genres(new HashSet<GenreDto>())
                .build();

        FilmResponse filmResponse = filmService.createFilm(dto);
        assertThat(filmResponse).hasFieldOrPropertyWithValue("id", 5L);
    }

    @Test
    public void testUpdateFilm() {
        FilmResponse filmResponse = filmService.getFilm(1L);
        FilmDto filmDto = FilmDto.builder()
                .id(filmResponse.getId())
                .name("newName")
                .description("newDescription")
                .releaseDate(filmResponse.getReleaseDate())
                .duration(filmResponse.getDuration())
                .build();

        filmResponse = filmService.updateFilm(filmDto);
        assertThat(filmResponse).hasFieldOrPropertyWithValue("name", "newName");
        assertThat(filmResponse).hasFieldOrPropertyWithValue("description", "newDescription");
    }

    @Test
    public void testGetPopular() {
        Assertions.assertEquals(new LikeResponse(3L, 1L), filmService.addLike(3L, 1L));
        Assertions.assertEquals(new LikeResponse(3L, 2L), filmService.addLike(3L, 2L));
        Assertions.assertEquals(new LikeResponse(2L, 1L), filmService.addLike(2L, 1L));

        List<FilmResponse> pFilms = filmService.getPopular(3);
        assertThat(pFilms)
                .hasSize(3)
                .extracting(FilmResponse::getName)
                .containsExactly("Фильм3", "Фильм2", "Фильм1");


        Assertions.assertTrue(filmService.removeLike(2L, 1L));
        pFilms = filmService.getPopular(3);
        assertThat(pFilms)
                .hasSize(3)
                .extracting(FilmResponse::getName)
                .containsExactly("Фильм3", "Фильм1", "Фильм2");
    }
}
