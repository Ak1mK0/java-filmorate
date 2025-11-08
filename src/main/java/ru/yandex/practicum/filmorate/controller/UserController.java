package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

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
    public Collection<User> getUserFriendList(@PathVariable @Positive long id) {
        log.trace("---------User getUserFriendList command---------");
        log.trace("---------ID пользователя = {}---------", id);
        return userService.getUserFriendList(id);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public Collection<User> getCommonUserFriendList(@PathVariable @Positive long id,
                                                    @PathVariable @Positive long friendId) {
        log.trace("---------User getCommonUserFriendList command---------");
        log.trace("---------ID пользователя = {}, ID друга = {}---------", id, friendId);
        return userService.getCommonUserFriendList(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addUserFriend(@PathVariable @Positive long id,
                              @PathVariable @Positive long friendId) {
        log.trace("---------User addUserFriend command---------");
        log.trace("---------ID пользователя = {}, ID друга = {}---------", id, friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeUserFriend(@PathVariable @Positive long id,
                                 @PathVariable @Positive long friendId) {
        log.trace("---------User removeUserFriend command---------");
        log.trace("---------ID пользователя = {}, ID друга = {}---------", id, friendId);
        userService.removeFriend(id, friendId);
    }
}
