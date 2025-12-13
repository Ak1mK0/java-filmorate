package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film f = Film.builder()
                .id(resultSet.getLong("film_id"))
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
        return f;
    }
}

