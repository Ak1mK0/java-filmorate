package ru.yandex.practicum.filmorate.model;

<<<<<<< HEAD
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
=======
import lombok.Getter;
import lombok.Setter;

/**
 * Film.
 */
@Getter
@Setter
public class Film {
>>>>>>> origin/main
}
