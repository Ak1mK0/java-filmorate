package ru.yandex.practicum.filmorate.dal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> {
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM Users WHERE user_login = ?";
        return findOne(query, login);
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
}
