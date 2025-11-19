package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

@Repository
public class FilmRepository extends BaseRepository<Film> {

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public Film save(Film film) {
        String query = "INSERT INTO Films (film_name, film_description, film_release_date, film_duration, film_mpa_id)" +
                "VALUES (?, ?, ?, ?, ?)";
        long id = insert(
                query,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());
        film.setId(id);
        return film;
    }
}
