package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.zalando.logbook.Logbook;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        log.trace("---------User findAll command---------");
        return userService.findAll();
    }

    @PostMapping
    public User addNewUser(@Valid @RequestBody User user) {
        log.trace("---------User addNewUser command---------");
        return userService.addNewUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.trace("---------User updateUser command---------");
        return userService.updateUser(user);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUserFriendList(@PathVariable long id) {
        log.trace("---------User getUserFriendList command---------");
        log.trace("---------ID пользователя = {}---------", id);
        return userService.getUserFriendList(id);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public Collection<User> getCommonUserFriendList(@PathVariable long id, @PathVariable long friendId) {
        log.trace("---------User getCommonUserFriendList command---------");
        log.trace("---------ID пользователя = {}, ID друга = {}---------", id, friendId);
        return userService.getCommonUserFriendList(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addUserFriend(@PathVariable long id, @PathVariable long friendId) {
        log.trace("---------User addUserFriend command---------");
        log.trace("---------ID пользователя = {}, ID друга = {}---------", id, friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeUserFriend(@PathVariable long id, @PathVariable long friendId) {
        log.trace("---------User removeUserFriend command---------");
        log.trace("---------ID пользователя = {}, ID друга = {}---------", id, friendId);
        userService.removeFriend(id, friendId);
    }
}
