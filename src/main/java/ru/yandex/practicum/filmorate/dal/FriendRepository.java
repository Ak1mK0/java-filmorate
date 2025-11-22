package ru.yandex.practicum.filmorate.dal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friend;

import java.util.List;
import java.util.Optional;

@Repository
public class FriendRepository extends BaseRepository<Friend> {
    private static final Logger log = LoggerFactory.getLogger(FriendRepository.class);


    public FriendRepository(JdbcTemplate jdbc, RowMapper<Friend> mapper) {
        super(jdbc, mapper);
    }

    public Friend save(Friend friend) {
        String query = "INSERT INTO Friends (user_id, friend_id)" +
                "VALUES (?, ?)";
        update(query, friend.getUserId(), friend.getFriendId());
        return friend;
    }

    public void remove(long userId, long friendId) {
        String query = "DELETE FROM Friends " +
                "WHERE user_id = ? " +
                "AND friend_id = ?";
        delete(query, userId, friendId);
    }

    public Optional<Friend> findFriend(Friend friend) {
        String query = "SELECT * " +
                "FROM Friends " +
                "WHERE user_id = ? " +
                "AND friend_id = ?";
        return findOne(query, friend.getUserId(), friend.getFriendId());
    }

    public List<Friend> getFriends(long userId) {
        String query = "SELECT * " +
                "FROM Friends " +
                "WHERE user_id = ? ";
        return findMany(query, userId);
    }

    public List<Friend> getCommonFriends(long userId, long friendId) {
        String query = "SELECT f1.* " +
                "FROM Friends AS f1 " +
                "INNER JOIN Friends AS f2 ON f1.friend_id = f2.friend_id " +
                "WHERE f1.user_id <> f2.user_id " +
                "AND f1.user_id = ? " +
                "AND f2.user_id = ? " +
                "ORDER BY f1.user_id";
        return findMany(query, userId, friendId);
    }
}
