package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmGenreRowMapper implements RowMapper<FilmGenre> {
    @Override
    public FilmGenre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        FilmGenre genre = new FilmGenre();
        genre.setFilmId(resultSet.getLong("film_id"));
        genre.setGenreId(resultSet.getInt("genre_id"));
        genre.setGenreName(resultSet.getString("genre_name"));
        return genre;
    }
}