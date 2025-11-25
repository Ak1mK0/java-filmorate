package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> {

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper, FilmGenresRepository filmGenresRepository) {
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

    public void update(Film film) {
        String query = "UPDATE Films " +
                "SET film_name = ?, " +
                "film_description = ?, " +
                "film_release_date = ?, " +
                "film_duration = ?, " +
                "film_mpa_id = ? " +
                "WHERE film_id = ?";
        update(query,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
    }

    public Optional<Film> getFilm(long id) {
        String query = "SELECT f.*, fr.rating_name " +
                "FROM Films AS f " +
                "LEFT JOIN Film_rating AS fr ON f.film_mpa_id = fr.rating_id " +
                "WHERE f.film_id = ? " +
                "ORDER BY f.film_id";
        return findOne(query, id);
    }

    public List<Film> getAll() {
        String query = "SELECT f.*, fr.rating_name " +
                "FROM Films AS f " +
                "LEFT JOIN Film_rating AS fr ON f.film_mpa_id = fr.rating_id " +
                "ORDER BY f.film_id";
        return findMany(query);
    }

    public List<Film> getPopular(long count) {
        String query = "SELECT f.*, fr.rating_name " +
                "FROM Films AS f " +
                "LEFT JOIN Film_rating AS fr ON f.film_mpa_id = fr.rating_id " +
                "LEFT JOIN Film_like fl ON f.film_id = fl.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(fl.film_id) DESC, f.film_id " +
                "LIMIT ?";
        return findMany(query, count);
    }
}
