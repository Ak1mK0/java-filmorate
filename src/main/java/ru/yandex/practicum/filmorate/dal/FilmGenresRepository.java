package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class FilmGenresRepository extends BaseRepository<FilmGenre>{


    public FilmGenresRepository(JdbcTemplate jdbc, RowMapper<FilmGenre> mapper) {
        super(jdbc, mapper);
    }

    public void updateFilmGenre(Long filmId, Set<Genre> genres) {
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
}
