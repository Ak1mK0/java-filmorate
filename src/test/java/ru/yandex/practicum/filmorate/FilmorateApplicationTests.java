package ru.yandex.practicum.filmorate;

<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.Validators;

import java.time.LocalDate;
=======
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
>>>>>>> origin/main

@SpringBootTest
class FilmorateApplicationTests {

	@Test
	void contextLoads() {
	}

<<<<<<< HEAD
	@Test
	void createNewFilmValidatorTest() {
		Film film = Film.builder()
				.name("name")
				.description("description")
				.releaseDate(LocalDate.of(1998,12,11))
				.duration(128L)
				.build();

		assertDoesNotThrow(() -> Validators.validateFilm(film));
	}

	@Test
	void emptyFilmNameValidatorTest() {
		Film film = Film.builder()
				.name("")
				.description("description")
				.releaseDate(LocalDate.of(1998,12,11))
				.duration(128L)
				.build();

		assertThrows(ConditionsNotMetException.class, () -> Validators.validateFilm(film));
	}

	@Test
	void blankFilmNameValidatorTest() {
		Film film = Film.builder()
				.name(" ")
				.description("description")
				.releaseDate(LocalDate.of(1998,12,11))
				.duration(128L)
				.build();

		assertThrows(ConditionsNotMetException.class, () -> Validators.validateFilm(film));
	}

	@Test
	void longDescriptionFilmNameValidatorTest() {
		String text = "A".repeat(200);
		Film film = Film.builder()
				.name(" ")
				.description(text)
				.releaseDate(LocalDate.of(1998,12,11))
				.duration(128L)
				.build();

		assertThrows(ConditionsNotMetException.class, () -> Validators.validateFilm(film));
	}

	@Test
	void wrongReleaseDateFilmNameValidatorTest() {
		Film film = Film.builder()
				.name(" ")
				.description("description")
				.releaseDate(LocalDate.of(1895,12,27))
				.duration(128L)
				.build();

		assertThrows(ConditionsNotMetException.class, () -> Validators.validateFilm(film));
	}

	@Test
	void correctUserValidatorTest() {
		User user = User.builder()
				.email("Test@email.ru")
				.login("login")
				.name("name")
				.birthday(LocalDate.of(2000,10,10))
				.build();

		assertDoesNotThrow(() -> Validators.validateUser(user));
	}

	@Test
	void emptyUserNameValidatorTest() {
		User user = User.builder()
				.email("Test@email.ru")
				.name("")
				.login("login")
				.birthday(LocalDate.of(2000,10,10))
				.build();

		assertDoesNotThrow(() -> Validators.validateUser(user));
	}

	@Test
	void blankUserNameValidatorTest() {
		User user = User.builder()
				.email("Test@email.ru")
				.name(" ")
				.login("login")
				.birthday(LocalDate.of(2000,10,10))
				.build();

		assertDoesNotThrow(() -> Validators.validateUser(user));
	}

	@Test
	void emptyEmailValidatorTest() {
		User user = User.builder()
				.email("")
				.login("login")
				.name("name")
				.birthday(LocalDate.of(2000,10,10))
				.build();

		assertThrows(ConditionsNotMetException.class, () -> Validators.validateUser(user));
	}

	@Test
	void wrongEmailValidatorTest() {
		User user = User.builder()
				.email("Testemail.ru")
				.login("login")
				.name("name")
				.birthday(LocalDate.of(2000,10,10))
				.build();

		assertThrows(ConditionsNotMetException.class, () -> Validators.validateUser(user));
	}

	@Test
	void wrongLoginUserValidatorTest() {
		User user = User.builder()
				.email("Test@email.ru")
				.login("login login")
				.name("name")
				.birthday(LocalDate.of(2000,10,10))
				.build();

		assertThrows(ConditionsNotMetException.class, () -> Validators.validateUser(user));
	}

	@Test
	void wrongBirthdayUserValidatorTest() {
		User user = User.builder()
				.email("Test@email.ru")
				.login("login login")
				.name("name")
				.birthday((LocalDate.now().plusDays(1)))
				.build();

		assertThrows(ConditionsNotMetException.class, () -> Validators.validateUser(user));
	}
=======
>>>>>>> origin/main
}
