package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MpaMapper {
    private static final Logger log = LoggerFactory.getLogger(MpaMapper.class);

    public static Mpa mapToMpa(MpaDto mpaDto) {
        Mpa mpa = new Mpa();
        mpa.setId(mpaDto.getId());
        mpa.setName(mpaDto.getName());
        return mpa;
    }

    public static MpaDto mapToDto(Mpa mpa) {
        MpaDto dto = new MpaDto();
        dto.setId(mpa.getId());
        dto.setName(mpa.getName());
        return dto;
    }
}
