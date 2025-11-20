package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.ValidateDate;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class FilmDto {
    @NotNull(message = "Название фильма не может быть пустым")
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    @Size(
            min = 1,
            max = 200,
            message = "Описание должно содержать до {max} символов")
    private String description;
    @ValidateDate(
            beforeOrAfter = ValidateDate.BeforeOrAfter.isBefore,
            year = 1895,
            month = 12,
            day = 28,
            message = "Дата релиза — не раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Min(value = 1,
            message = "Длительность не может быть отрицательно")
    private Integer duration;
    private MpaDto mpa;
    private Set<GenreDto> genres;
}
