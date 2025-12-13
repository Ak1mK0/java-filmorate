package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class GenreRepository extends BaseRepository<Genre> {

    public GenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public List<Genre> findAll() {
        String query = "SELECT * FROM Genres ORDER BY genre_id";
        return findMany(query);
    }

    public Optional<Genre> findById(Integer id) {
        String query = "SELECT * FROM Genres WHERE genre_id = ?";
        return findOne(query, id);
    }

    public int countExist(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        String idsText = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        String query = "SELECT COUNT(*) FROM Genres WHERE genre_id IN (" + idsText + ")";
        return countAny(query);
    }
}
