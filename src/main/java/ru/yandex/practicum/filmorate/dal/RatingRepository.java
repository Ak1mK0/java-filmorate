package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Repository
public class RatingRepository extends BaseRepository<Mpa> {

    public RatingRepository(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
        super(jdbc, mapper);
    }

    public List<Mpa> findAll() {
        String query = "SELECT * FROM Film_rating ORDER BY rating_id";
        return findMany(query);
    }

    public Optional<Mpa> findById(Integer id) {
        String query = "SELECT * FROM Film_rating WHERE rating_id = ?";
        return findOne(query, id);
    }
}
