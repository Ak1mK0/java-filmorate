package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.dto.FriendResponse;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.UserResponse;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan("ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceFilmoRateApplicationTests {
    private final UserService userService;

    @Test
    public void testCreateUser() {
        UserDto userDto = UserDto.builder()
                .login("user4Login")
                .email("user4@mail.com")
                .name("user4Name")
                .birthday(LocalDate.of(1990, 5, 21))
                .build();

        UserResponse userResponse = userService.createUser(userDto);
        assertThat(userResponse).hasFieldOrPropertyWithValue("id", 4L);
    }

    @Test
    public void testUpdateUser() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .login("user4Login")
                .email("user4@mail.com")
                .name("")
                .birthday(LocalDate.of(1990, 5, 21))
                .build();

        UserResponse userResponse = userService.updateUser(userDto);
        assertThat(userResponse).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(userResponse).hasFieldOrPropertyWithValue("login", "user4Login");
        assertThat(userResponse).hasFieldOrPropertyWithValue("email", "user4@mail.com");
        assertThat(userResponse).hasFieldOrPropertyWithValue("name", "user4Login");
    }

    @Test
    public void testAddFriendship() {
        userService.addFriendship(1, 3);
        List<FriendResponse> fl1 = userService.findFriends(1);
        List<FriendResponse> fl2 = userService.findFriends(3);

        assertThat(fl1)
                .anyMatch(friend -> friend.getId() == 3);
        Assertions.assertTrue(fl2.isEmpty());
    }

    @Test
    public void testRemoveFriendship() {
        List<FriendResponse> fl = userService.findFriends(2);
        assertThat(fl)
                .anyMatch(friend -> friend.getId() == 3);
        userService.deleteFriendship(2, 3);
        fl = userService.findFriends(2);
        Assertions.assertTrue(fl.isEmpty());
    }

    @Test
    public void testGetCommonFriends() {
        userService.addFriendship(1, 3);
        List<FriendResponse> fl = userService.getCommonFriends(1, 2);
        assertThat(fl)
                .anyMatch(friend -> friend.getId() == 3);
    }
}
