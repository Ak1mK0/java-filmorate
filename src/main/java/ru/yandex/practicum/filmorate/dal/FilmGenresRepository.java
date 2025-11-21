package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class FilmGenresRepository extends BaseRepository<Genre> {


    public FilmGenresRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public List<Genre> findFilmGenres(Long filmId) {
        String query = "SELECT fg.genre_id, g.genre_name " +
                "FROM FILM_GENRES AS fg " +
                "LEFT JOIN Genres AS g ON fg.genre_id = g.genre_id " +
                "WHERE fg.film_id = ?";
        return findMany(query, filmId);
    }

    public void updateFilmGenre(Long filmId, Set<Genre> genres) {
        delete(filmId);
        if (genres != null && !genres.isEmpty()) {
            StringBuilder query = new StringBuilder(
                    "INSERT INTO Film_genres (film_id, genre_id) VALUES "
            );
            List<Object> params = new ArrayList<>();
            int index = 0;
            for (Genre genre : genres) {
                if (index > 0) query.append(", ");
                query.append("(?, ?)");
                params.add(filmId);
                params.add(genre.getId());
                index++;
            }
            update(query.toString(), params.toArray());
        }
    }

    public boolean delete(long filmId) {
        String query = "DELETE FROM Film_genres " +
                "WHERE film_id = ?";
        return delete(query, filmId);
    }
}
