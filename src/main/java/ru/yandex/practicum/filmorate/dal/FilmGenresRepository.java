package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public class FilmGenresRepository extends BaseRepository<FilmGenre> {

    public FilmGenresRepository(JdbcTemplate jdbc, RowMapper<FilmGenre> mapper, JdbcTemplate jdbc1) {
        super(jdbc, mapper);
    }

    public List<FilmGenre> findFilmGenres(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        String symbol = String.join(",",
                Collections.nCopies(ids.size(), "?"));
        String query = "SELECT fg.*, g.genre_name " +
                "FROM film_genres AS fg " +
                "INNER JOIN genres AS g ON fg.genre_id = g.genre_id " +
                "WHERE fg.film_id IN (" + symbol + ") " +
                "ORDER BY fg.film_id ";
        return findMany(query, ids.toArray());
    }

    public void updateFilmGenre(Long filmId, Set<Genre> genres) {
        delete(filmId);
        ArrayList<Genre> genreList = new ArrayList<>(genres);
        jdbc.batchUpdate(
                "INSERT INTO Film_genres (film_id, genre_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, filmId);
                        ps.setLong(2, genreList.get(i).getId());
                    }

                    public int getBatchSize() {
                        return genreList.size();
                    }
                });
    }

    public boolean delete(long filmId) {
        String query = "DELETE FROM Film_genres " +
                "WHERE film_id = ?";
        return delete(query, filmId);
    }
}
