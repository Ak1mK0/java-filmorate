package ru.yandex.practicum.filmorate.dal;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class UserRepository extends BaseRepository<User> {

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM Users WHERE user_login = ?";
        return findOne(query, login);
    }

    public Optional<User> findById(Long userId) {
        String query = "SELECT * FROM Users WHERE user_id = ?";
        return findOne(query, userId);
    }

    public User save(User user) {
        String query = "INSERT INTO Users (user_email, user_login, user_name, user_birthday)" +
                "VALUES (?, ?, ?, ?)";
        long id = insert(
                query,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        user.setId(id);
        return user;
    }

    public User update(User user) {
        String query = "UPDATE Users " +
                "SET user_email = ?, " +
                "user_login = ?, " +
                "user_name = ?, " +
                "user_birthday = ? " +
                "WHERE user_id = ?";
        update(query,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    public List<User> findAll() {
        String query = "SELECT * " +
                "FROM Users " +
                "ORDER BY user_id";
        return findMany(query);
    }
}
