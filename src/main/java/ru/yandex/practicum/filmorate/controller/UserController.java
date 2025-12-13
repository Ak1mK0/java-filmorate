package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FriendResponse;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.UserResponse;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid UserDto userDto) {
        log.debug("--Новый запрос createUser--");
        log.debug("User data: {}", userDto);
        return userService.createUser(userDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@RequestBody @Valid UserDto userDto) {
        log.debug("--Новый запрос updateUser--");
        log.debug("User data: {}", userDto);
        return userService.updateUser(userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAll() {
        log.debug("--Новый запрос getAll--");
        return userService.getAll();
    }

    @PutMapping("/{userId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public FriendResponse addFiendship(@PathVariable long userId, @PathVariable long friendId) {
        log.debug("--Новый запрос addFiendship--");
        log.debug("Пользователь: {}, друг {}", userId, friendId);
        return userService.addFriendship(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<FriendResponse> getFiendList(@PathVariable long userId) {
        log.debug("--Новый запрос getFiendList--");
        log.debug("Пользователь: {}", userId);
        return userService.findFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public List<FriendResponse> getCommonFriends(@PathVariable long userId, @PathVariable long friendId) {
        log.debug("--Новый запрос addFiendship--");
        log.debug("Пользователь: {}, друг {}", userId, friendId);
        return userService.getCommonFriends(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFiendship(@PathVariable long userId, @PathVariable long friendId) {
        log.debug("--Новый запрос removeFiendship--");
        log.debug("Пользователь: {}, друг {}", userId, friendId);
        userService.deleteFriendship(userId, friendId);
    }
}
