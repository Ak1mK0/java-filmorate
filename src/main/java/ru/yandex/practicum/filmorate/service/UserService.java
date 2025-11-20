package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dto.UserResponse;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserResponse createUser(UserDto userDto) {
        if (userRepository.findByLogin(userDto.getLogin()).isPresent()) {
            throw new ObjectAlreadyExistException("Логин " + userDto.getLogin() + " уже занят");
        }
        User user = UserMapper.mapToUser(userDto);
        userRepository.save(user);
        return UserMapper.mapToNewUserResponse(user);
    }

    private User userExistCheck(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new ObjectNotFindException("Пользователь с логином: " + login + " не существует");
        }
        return optionalUser.get();
    }
}
