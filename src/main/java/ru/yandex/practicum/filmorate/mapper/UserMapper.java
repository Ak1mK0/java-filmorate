package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.dto.UserResponse;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    private static final Logger log = LoggerFactory.getLogger(UserMapper.class);

    public static User mapToUser(UserDto dto) {
        User u = new User();
        u.setEmail(dto.getEmail());
        u.setLogin(dto.getLogin());
        u.setBirthday(dto.getBirthday());
        if (dto.getName() == null) {
            u.setName(dto.getLogin());
        } else {
            u.setName(dto.getName());
        }
        return u;
    }

    public static UserResponse mapToNewUserResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setBirthday(user.getBirthday());
        return dto;
    }
}
