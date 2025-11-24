package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dto.FriendResponse;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.UserResponse;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceFilmoRateApplicationTests {

    private final UserService userService;
    private final UserRepository userRepository;

    @Test
    public void testSaveNewUser() {

        UserDto dto = UserDto.builder()
                .login("u4Login")
                .email("u4.@mail.com")
                .birthday(LocalDate.of(1995, 8, 11))
                .name("u4Name")
                .build();
        UserResponse userResponse = userService.createUser(dto);

        Optional<User> userOptional = userRepository.findById(userResponse.getId());

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u).hasFieldOrPropertyWithValue("id", userResponse.getId())
                );
    }

    @Test
    public void testUpdateUser() {

        Optional<User> userOptional = userRepository.findById(1L);

        User user = userOptional.get();
        UserDto dto = UserDto.builder()
                .id(user.getId())
                .login("u1NewLogin")
                .email("u1New.@mail.com")
                .birthday(user.getBirthday())
                .build();
        UserResponse userResponse = userService.updateUser(dto);

        assertThat(userResponse).hasFieldOrPropertyWithValue("email", "u1New.@mail.com");
        assertThat(userResponse).hasFieldOrPropertyWithValue("login", "u1NewLogin");
        assertThat(userResponse).hasFieldOrPropertyWithValue("name", "u1NewLogin");
    }

    @Test
    public void testAddAndRemoveFriendship() {
        userService.addFriendship(1L, 2L);
        List<FriendResponse> fL1 = userService.findFriends(1L);
        List<FriendResponse> fL2 = userService.findFriends(2L);
        Assertions.assertTrue(fL1.contains(new FriendResponse(2L)));
        Assertions.assertTrue(fL2.isEmpty());

        userService.deleteFriendship(1L, 2L);
        fL1 = userService.findFriends(1L);
        Assertions.assertTrue(fL1.isEmpty());
        Assertions.assertTrue(fL2.isEmpty());
    }

    @Test
    public void testFindCommonFriends() {
        userService.addFriendship(1L, 2L);
        userService.addFriendship(1L, 3L);
        userService.addFriendship(2L, 3L);
        List<FriendResponse> fC = userService.getCommonFriends(1L, 2L);
        Assertions.assertTrue(fC.contains(new FriendResponse(3L)));
    }

} 