package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long firstLineId = resultSet.getLong("film_id");
        Film f = Film.builder()
                .id(firstLineId)
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("film_description"))
                .releaseDate(resultSet.getDate("film_release_date").toLocalDate())
                .duration(resultSet.getInt("film_duration"))
                .mpa(new Mpa())
                .genres(new HashSet<>())
                .build();

        Integer mpaId = resultSet.getInt("film_mpa_id");
        if (mpaId != null) {
            f.setMpa(new Mpa(
                    resultSet.getInt("film_mpa_id"),
                    resultSet.getString("rating_name")));
        }

        Set<Genre> genres = new HashSet<>();
        Long currentFilmId = f.getId();
        do {
            int genreId = resultSet.getInt("genre_id");
            if (!resultSet.wasNull()) {
                genres.add(new Genre(
                        resultSet.getInt("genre_id"),
                        resultSet.getString("genre_name")));
            }
        } while (resultSet.next() && currentFilmId.equals(resultSet.getLong("film_id")));

        if (!resultSet.isAfterLast()) {
            resultSet.previous();
        }

        f.setGenres(genres);
        return f;
    }
}

