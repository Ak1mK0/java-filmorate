package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
public class InMemoryUserStorage implements UserStorage {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);

    private final Map<Long, User> users = new HashMap<>();

    public User add(User user) {
        log.trace("Добавление пользователя");
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    public User update(User user) {
        log.trace("Обновление пользователя с id: {}", user.getId());
        users.put(user.getId(), user);
        return user;
    }

    public void remove(long id) {
        log.trace("Удаление пользователя с id: {}", id);
        users.remove(id);
    }

    public Optional<User> get(long id) {
        log.trace("Получение пользователя с id: {}", id);
        return Optional.ofNullable(users.get(id));
    }

    public Collection<User> getAll() {
        log.trace("Получение списка пользователей");
        return users.values();
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
