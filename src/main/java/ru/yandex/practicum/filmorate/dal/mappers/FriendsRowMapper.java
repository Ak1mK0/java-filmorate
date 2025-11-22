package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friend;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FriendsRowMapper implements RowMapper<Friend> {
    @Override
    public Friend mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Friend f = new Friend();
        f.setUserId(resultSet.getLong("user_id"));
        f.setFriendId(resultSet.getLong("friend_id"));
        return f;
    }
}