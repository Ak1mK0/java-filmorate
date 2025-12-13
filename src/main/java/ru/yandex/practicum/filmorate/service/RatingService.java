package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.RatingRepository;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private static final Logger log = LoggerFactory.getLogger(RatingService.class);
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<MpaDto> getRatings() {
        return ratingRepository.findAll().stream()
                .map(MpaMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public MpaDto findRating(int id) {
        return ratingExistCheck(id);
    }

    private MpaDto ratingExistCheck(int id) {
        Optional<Mpa> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isEmpty()) {
            throw new ObjectNotFindException("Рейтинга с id: " + id + " не существует");
        }
        return MpaMapper.mapToDto(optionalRating.get());
    }

}
