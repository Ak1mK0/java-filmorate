//package ru.yandex.practicum.filmorate;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.yandex.practicum.filmorate.controller.UserController;
//import ru.yandex.practicum.filmorate.model.User;
//
//import java.time.LocalDate;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//class FilmorateUserApplicationTests {
//
//    private Validator validator;
//    String messageException;
//    User testUser;
//    UserController userController = new UserController();
//
//    @BeforeEach
//    public void beforeEach() {
//		testUser = User.builder()
//				.email("Test@email.ru")
//				.login("login")
//				.name("name")
//				.birthday(LocalDate.of(2000,10,10))
//                .build();
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//
//    public void readException() {
//        Set<ConstraintViolation<User>> violations = validator.validate(testUser);
//        for (ConstraintViolation<User> viol : violations) {
//            messageException = viol.getMessage();
//        }
//    }
//
//	@Test
//	void correctUserValidatorTest() {
//		userController.addNewUser(testUser);
//		assertTrue(userController.findAll().contains(testUser));
//	}
//
//	@Test
//	void emptyUserNameValidatorTest() {
//		testUser.setName("");
//		userController.addNewUser(testUser);
//		assertTrue(userController.findAll().contains(testUser));
//	}
//
//	@Test
//	void blankUserNameValidatorTest() {
//		testUser.setName(" ");
//		userController.addNewUser(testUser);
//		assertTrue(userController.findAll().contains(testUser));
//	}
//
//	@Test
//	void emptyEmailValidatorTest() {
//		testUser.setEmail(null);
//
//		readException();
//		assertEquals("Адрес пользователя не может быть пустым", messageException);
//	}
//
//	@Test
//	void blankEmailValidatorTest() {
//		testUser.setEmail(" ");
//
//		readException();
//		assertEquals("Не корректный формат электронная почты", messageException);
//	}
//
//	@Test
//	void wrongEmailValidatorTest() {
//		testUser.setEmail("TestEmail.ru");
//
//		readException();
//		assertEquals("Не корректный формат электронная почты", messageException);
//	}
//
//	@Test
//	void wrongEmailConstructionValidatorTest() {
//		testUser.setEmail("TestEmail@.ru");
//
//		readException();
//		assertEquals("Не корректный формат электронная почты", messageException);
//	}
//
//	@Test
//	void blankLoginUserValidatorTest() {
//		testUser.setLogin(" ");
//
//		readException();
//		assertEquals("Логин пользователя не должен содержит пробелы", messageException);
//	}
//
//	@Test
//	void emptyLoginUserValidatorTest() {
//		testUser.setLogin(null);
//
//		readException();
//		assertEquals("Логин пользователя не может быть пустым", messageException);
//	}
//
//	@Test
//	void wrongBirthdayUserValidatorTest() {
//		testUser.setBirthday((LocalDate.now().plusDays(1)));
//
//		readException();
//		assertEquals("Дата не может быть в будущем", messageException);
//	}
//}
