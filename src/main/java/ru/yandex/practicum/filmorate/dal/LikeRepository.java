package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Like;

import java.util.Optional;

@Repository
public class LikeRepository extends BaseRepository<Like> {

    public LikeRepository(JdbcTemplate jdbc, RowMapper<Like> mapper) {
        super(jdbc, mapper);
    }

    public Like save(long filmId, long userId) {
        String query = "INSERT INTO Film_like (user_id, film_id) " +
                "VALUES (?, ?)";
        update(query, userId, filmId);
        return new Like(userId, filmId);
    }

    public boolean delete(long filmId, long userId) {
        String query = "DELETE FROM Film_like " +
                "WHERE film_id = ? " +
                "AND user_id = ? ";
        return delete(query, userId, filmId);
    }

    public Optional<Like> findOne(long filmId, long userId) {
        String query = "SELECT * FROM Film_like WHERE " +
                "film_id = ? " +
                "AND user_id = ?";
        return findOne(query, filmId, userId);
    }
}
