package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import ru.yandex.practicum.filmorate.dto.FilmDto;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDataFilmoRateApplicationTests {

    String messageException;
    FilmDto testFilm;
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        testFilm = FilmDto.builder()
                .name("TestName")
                .description("DescriptionName")
                .releaseDate(LocalDate.of(2000, 10, 10))
                .duration(40)
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void readException() {
        Set<ConstraintViolation<FilmDto>> violations = validator.validate(testFilm);
        for (ConstraintViolation<FilmDto> viol : violations) {
            messageException = viol.getMessage();
        }
    }

    @Test
    void emptyFilmNameValidatorTest() {
        testFilm.setName(null);
        readException();
        assertEquals("Название фильма не может быть пустым", messageException);
    }

    @Test
    void blankFilmNameValidatorTest() {
        testFilm.setName(" ");
        readException();
        assertEquals("Название фильма не может быть пустым", messageException);
    }

    @Test
    void longDescriptionFilmNameValidatorTest() {
        testFilm.setDescription("A".repeat(201));
        readException();
        assertEquals("Описание должно содержать до 200 символов", messageException);
    }

    @Test
    void wrongReleaseDateFilmNameValidatorTest() {
        testFilm.setReleaseDate(LocalDate.of(1895, 12, 27));
        readException();
        assertEquals("Дата релиза — не раньше 28 декабря 1895 года", messageException);
    }
}