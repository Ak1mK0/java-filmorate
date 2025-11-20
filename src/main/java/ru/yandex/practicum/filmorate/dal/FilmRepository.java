package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Repository
public class FilmRepository extends BaseRepository<Film> {
    private final FilmGenresRepository filmGenresRepository;

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper, FilmGenresRepository filmGenresRepository) {
        super(jdbc, mapper);
        this.filmGenresRepository = filmGenresRepository;
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
        filmGenresRepository.updateFilmGenre(film.getId(), film.getGenres());
        return film;
    }

    public Optional<Film> getFilm(long id) {
        String query = "SELECT f.*, fr.rating_name, fg.genre_id, g.genre_name " +
                "FROM Films AS f " +
                "LEFT JOIN Film_rating AS fr ON f.film_mpa_id = fr.rating_id " +
                "LEFT JOIN Film_genres AS fg ON f.film_id = fg.film_id " +
                "LEFT JOIN Genres AS g ON fg.genre_id = g.genre_id " +
                "WHERE f.film_id = ? " +
                "ORDER BY f.film_id";
        return findOne(query, id);
    }

    public Set<Film> getAll() {
        return Collections.emptySet();
    }
}
