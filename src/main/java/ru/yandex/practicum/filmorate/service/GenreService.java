package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> getGenres() {
        return genreRepository.findAll().stream()
                .map(GenreMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public GenreDto findGenre(int id) {
        return ratingExistCheck(id);
    }

    private GenreDto ratingExistCheck(int id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isEmpty()) {
            throw new ObjectNotFindException("Жанра с id: " + id + " не существует");
        }
        return GenreMapper.mapToDto(optionalGenre.get());
    }
}
