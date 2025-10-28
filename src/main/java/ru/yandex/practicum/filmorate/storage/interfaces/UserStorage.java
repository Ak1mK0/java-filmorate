package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    public User add(User user);

    public Optional<User> get(long id);

    public Collection<User> getAll();

    public User update(User user);

    public void remove(long user);
}
