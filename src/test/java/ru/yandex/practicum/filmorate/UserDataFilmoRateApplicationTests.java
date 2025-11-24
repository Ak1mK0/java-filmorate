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
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDataFilmoRateApplicationTests {

    String messageException;
    UserDto testUser;
    private Validator validator;
    ;

    @BeforeEach
    public void beforeEach() {
        testUser = UserDto.builder()
                .email("Test@email.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2000, 10, 10))
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void readException() {
        Set<ConstraintViolation<UserDto>> violations = validator.validate(testUser);
        for (ConstraintViolation<UserDto> viol : violations) {
            messageException = viol.getMessage();
        }
    }

    @Test
    void emptyUserNameValidatorTest() {
        testUser.setName("");
        User user = UserMapper.mapToUser(testUser);
        assertEquals(user.getName(), testUser.getLogin());
    }

    @Test
    void blankUserNameValidatorTest() {
        testUser.setName(" ");
        User user = UserMapper.mapToUser(testUser);
        assertEquals(user.getName(), testUser.getLogin());
    }

    @Test
    void emptyEmailValidatorTest() {
        testUser.setEmail(null);

        readException();
        assertEquals("Адрес пользователя не может быть пустым", messageException);
    }

    @Test
    void blankEmailValidatorTest() {
        testUser.setEmail(" ");

        readException();
        assertEquals("Не корректный формат электронная почты", messageException);
    }

    @Test
    void wrongEmailValidatorTest() {
        testUser.setEmail("TestEmail.ru");

        readException();
        assertEquals("Не корректный формат электронная почты", messageException);
    }

    @Test
    void wrongEmailConstructionValidatorTest() {
        testUser.setEmail("TestEmail@.ru");

        readException();
        assertEquals("Не корректный формат электронная почты", messageException);
    }

    @Test
    void blankLoginUserValidatorTest() {
        testUser.setLogin(" ");

        readException();
        assertEquals("Логин пользователя не должен содержит пробелы", messageException);
    }

    @Test
    void emptyLoginUserValidatorTest() {
        testUser.setLogin(null);

        readException();
        assertEquals("Логин пользователя не может быть пустым", messageException);
    }

    @Test
    void wrongBirthdayUserValidatorTest() {
        testUser.setBirthday((LocalDate.now().plusDays(1)));

        readException();
        assertEquals("Дата не может быть в будущем", messageException);
    }
}
