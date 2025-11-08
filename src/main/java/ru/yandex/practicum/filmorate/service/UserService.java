package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserStorage userStorage;

    public User addNewUser(User user) {
        log.trace("User addNewUser command");
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.add(user);
    }

    public User updateUser(User user) {
        log.trace("User updateUser command");
        User oldUser = userExistCheck(user.getId());
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setFriends(oldUser.getFriends());
        return userStorage.update(user);
    }

    public void removeUser(User user) {
        log.trace("User removeUser command");
        userStorage.remove(user.getId());
    }

    public User findUserById(User user) {
        log.trace("User findUserById command");
        return userExistCheck(user.getId());
    }

    public Collection<User> findAll() {
        log.trace("User findAll command");
        return userStorage.getAll();
    }

    public void addFriend(long id, long friendId) {
        log.trace("User addFriend command");
        User idUser = userExistCheck(id);
        User friendUser = userExistCheck(friendId);
        log.trace("Пользователи получены");

        Set<Long> idUserFriendsList = Optional.ofNullable(idUser.getFriends())
                .orElse(new HashSet<>());
        log.trace("Список друзей пользователя {}: {}", id, idUserFriendsList);
        idUserFriendsList.add(friendId);
        idUser.setFriends(idUserFriendsList);
        log.trace("Новый список друзей пользователя {}: {}", id, idUserFriendsList);

        Set<Long> friendUserFriendsList = Optional.ofNullable(friendUser.getFriends())
                .orElse(new HashSet<>());
        ;
        log.trace("Список друзей пользователя {}: {}", friendId, friendUserFriendsList);
        friendUserFriendsList.add(id);
        friendUser.setFriends(friendUserFriendsList);
        log.trace("Новый список друзей пользователя {}: {}", friendId, friendUserFriendsList);
    }

    public void removeFriend(long id, long friendId) {
        log.trace("User removeFriend command");
        User idUser = userExistCheck(id);
        User friendUser = userExistCheck(friendId);
        log.trace("Пользователи получены");

        Set<Long> idUserFriendsList = Optional.ofNullable(idUser.getFriends())
                .orElse(new HashSet<>());
        log.trace("Список друзей пользователя {}: {}", id, idUserFriendsList);
        idUserFriendsList.remove(friendId);
        idUser.setFriends(idUserFriendsList);
        log.trace("Новый список друзей пользователя {}: {}", id, idUserFriendsList);

        Set<Long> friendUserFriendsList = Optional.ofNullable(friendUser.getFriends())
                .orElse(new HashSet<>());
        log.trace("Список друзей пользователя {}: {}", friendId, friendUserFriendsList);
        friendUserFriendsList.remove(id);
        friendUser.setFriends(friendUserFriendsList);
        log.trace("Новый список друзей пользователя {}: {}", friendId, friendUserFriendsList);
    }

    public Collection<User> getUserFriendList(long id) {
        log.trace("User getUserFriendList command");
        User user = userExistCheck(id);
        Set<Long> friendList = user.getFriends();
        return Optional.ofNullable(friendList)
                .orElse(new HashSet<>())
                .stream()
                .map(userStorage::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Collection<User> getCommonUserFriendList(long id, long friendId) {
        log.trace("User getCommonUserFriendList command");
        User idUser = userExistCheck(id);
        User friendUser = userExistCheck(friendId);
        log.trace("Пользователи получены");

        Set<Long> commonFriendIds = Optional.ofNullable(idUser.getFriends())
                .orElse(new HashSet<>())
                .stream()
                .filter(Optional.ofNullable(friendUser.getFriends())
                        .orElse(new HashSet<>())::contains)
                .collect(Collectors.toSet());

        return commonFriendIds.stream()
                .map(userStorage::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private User userExistCheck(long id) {
        Optional<User> optionalUser = userStorage.get(id);
        if (optionalUser.isEmpty()) {
            throw new ObjectNotFindException("Пользователя с id: " + id + " не существует");
        }
        return optionalUser.get();
    }
}
