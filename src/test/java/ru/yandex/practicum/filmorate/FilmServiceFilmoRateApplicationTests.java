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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmServiceFilmoRateApplicationTests {

    private final FilmService filmService;

    @Test
    public void testSaveFilm() {
        FilmDto dto = FilmDto.builder()
                .name("Film5")
                .description("Description5")
                .releaseDate(LocalDate.of(1999, 4, 25))
                .duration(78)
                .mpa(new MpaDto(3, null))
                .genres(Set.of(new GenreDto(1, null), new GenreDto(3, null)))
                .build();
        FilmResponse filmResponse = filmService.createFilm(dto);
        assertThat(filmResponse).hasFieldOrPropertyWithValue("id", 5L);
    }

    @Test
    public void testUpdateFilm() {
        FilmResponse filmResponse = filmService.getFilm(1L);
        FilmDto dto = FilmDto.builder()
                .id(filmResponse.getId())
                .name("NewName")
                .description("NewDescription")
                .releaseDate(filmResponse.getReleaseDate())
                .duration(filmResponse.getDuration())
                .mpa(new MpaDto(4, null))
                .genres(null)
                .build();

        filmResponse = filmService.updateFilm(dto);

        assertThat(filmResponse).hasFieldOrPropertyWithValue("name", "NewName");
        assertThat(filmResponse).hasFieldOrPropertyWithValue("description", "NewDescription");
        assertThat(filmResponse).hasFieldOrPropertyWithValue("mpa", new MpaDto(4, null));
        assertThat(filmResponse).hasFieldOrPropertyWithValue("genres", new HashSet<GenreDto>());
    }

    @Test
    public void testGetPopularFilms() {
        Assertions.assertEquals(new LikeResponse(3L, 1L), filmService.addLike(3L, 1L));
        Assertions.assertEquals(new LikeResponse(2L, 2L), filmService.addLike(2L, 2L));

        List<FilmResponse> pFilmList = filmService.getPopular(3);
        assertThat(pFilmList)
                .hasSize(3)
                .extracting(FilmResponse::getName)
                .containsExactly("Фильм2", "Фильм3", "Фильм1");

        Assertions.assertTrue(filmService.removeLike(2L, 1L));
        Assertions.assertTrue(filmService.removeLike(2L, 2L));

        List<FilmResponse> pFilmList2 = filmService.getPopular(3);
        assertThat(pFilmList2)
                .hasSize(3)
                .extracting(FilmResponse::getName)
                .containsExactly("Фильм3", "Фильм1", "Фильм2");
    }

} 