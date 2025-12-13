package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.dto.FriendResponse;
import ru.yandex.practicum.filmorate.model.Friend;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FriendMapper {
    private static final Logger log = LoggerFactory.getLogger(FriendMapper.class);

    public static FriendResponse mapToResponse(Friend friend) {
        FriendResponse dto = new FriendResponse();
        dto.setId(friend.getFriendId());
        return dto;
    }
}
