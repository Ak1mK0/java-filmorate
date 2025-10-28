package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserStorage userStorage;

    public User addNewUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.add(user);
    }

    public User updateUser(User user) {
        Optional<User> optionalUser= userStorage.get(user.getId());
        if (optionalUser.isEmpty()) {
            throw new ConditionsNotMetException("Пользователя с id: " + user.getId() + " не существует");
        }
        User oldUser = optionalUser.get();
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setFriends(oldUser.getFriends());
        return userStorage.update(user);
    }

    public void removeUser(User user) {
        userStorage.remove(user.getId());
    }

    public User findUserById(User user) {
        Optional<User> optionalUser= userStorage.get(user.getId());
        if (optionalUser.isEmpty()) {
            throw new ConditionsNotMetException("Пользователя с id: " + user.getId() + " не существует");
        }
        return optionalUser.get();
    }

    public Collection<User> findAll() {
        return userStorage.getAll();
    }

    public void addFriend(long id, long friendId) {
        Optional<User> optionalUser = userStorage.get(id);
        if (optionalUser.isEmpty()) {
            throw new ConditionsNotMetException("Пользователя с id: " + id + " не существует");
        }
        Optional<User> optionalFriendUser= userStorage.get(friendId);
        if (optionalFriendUser.isEmpty()) {
            throw new ConditionsNotMetException("Пользователя с id: " + friendId + " не существует");
        }
        User idUser = optionalUser.get();
        Set<Long> idUserFriendsList = idUser.getFriends();
        idUserFriendsList.add(friendId);

        User friendUser = optionalFriendUser.get();
        Set<Long> friendUserFriendsList = friendUser.getFriends();
        friendUserFriendsList.add(id);
    }

    public void removeFriend(long id, long friendId) {
        Optional<User> optionalUser = userStorage.get(id);
        if (optionalUser.isEmpty()) {
            throw new ConditionsNotMetException("Пользователя с id: " + id + " не существует");
        }
        Optional<User> optionalFriendUser= userStorage.get(friendId);
        if (optionalFriendUser.isEmpty()) {
            throw new ConditionsNotMetException("Пользователя с id: " + friendId + " не существует");
        }
        User idUser = optionalUser.get();
        Set<Long> idUserFriendsList = idUser.getFriends();
        idUserFriendsList.remove(friendId);

        User friendUser = optionalFriendUser.get();
        Set<Long> friendUserFriendsList = friendUser.getFriends();
        friendUserFriendsList.remove(id);
    }

    public Collection<User>  getFriendList(long id) {
        Optional<User> optionalUser = userStorage.get(id);
        if (optionalUser.isEmpty()) {
            throw new ConditionsNotMetException("Пользователя с id: " + id + " не существует");
        }
        User user = optionalUser.get();
        Set<Long> friendList = user.getFriends();
        return friendList.stream()
                .map(userStorage::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
