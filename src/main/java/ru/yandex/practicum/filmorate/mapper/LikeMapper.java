package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.LikeResponse;
import ru.yandex.practicum.filmorate.model.Like;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeMapper {

    public static LikeResponse mapToResponse(Like like) {
        LikeResponse dto = new LikeResponse();
        dto.setFilmId(like.getFilmId());
        dto.setUserId(like.getUserId());
        return dto;
    }
}
