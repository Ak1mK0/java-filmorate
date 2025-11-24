package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FriendRepository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dto.FriendResponse;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.UserResponse;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFindException;
import ru.yandex.practicum.filmorate.mapper.FriendMapper;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public UserResponse createUser(UserDto userDto) {
        if (userRepository.findByLogin(userDto.getLogin()).isPresent()) {
            throw new ObjectAlreadyExistException("Логин " + userDto.getLogin() + " уже занят");
        }
        User user = UserMapper.mapToUser(userDto);
        userRepository.save(user);
        return UserMapper.mapToUserResponse(user);
    }

    public UserResponse updateUser(UserDto userDto) {
        userExistIdCheck(userDto.getId());
        User user = UserMapper.mapToUser(userDto);
        userRepository.update(user);

        return UserMapper.mapToUserResponse(user);
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public FriendResponse addFriendship(long userId, long friendId) {
        userExistIdCheck(userId);
        userExistIdCheck(friendId);
        Friend friend = new Friend(userId, friendId);
        if (friendRepository.findFriend(friend).isEmpty() && userId != friendId) {
            friendRepository.save(friend);

            return FriendMapper.mapToResponse(friend);

        } else {
            throw new ObjectAlreadyExistException("Дружба между пользователями "
                    + userId + " и " + friendId +
                    "не может быть реализована");
        }
    }

    public List<FriendResponse> findFriends(long userId) {
        userExistIdCheck(userId);
        List<Friend> friends = friendRepository.getFriends(userId);

        return friends.stream()
                .map(FriendMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<FriendResponse> getCommonFriends(long userId, long friendId) {
        userExistIdCheck(userId);
        userExistIdCheck(friendId);

        List<Friend> friends = friendRepository.getCommonFriends(userId, friendId);
        return friends.stream()
                .map(FriendMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteFriendship(long userId, long friendId) {
        userExistIdCheck(userId);
        userExistIdCheck(friendId);
        friendRepository.remove(userId, friendId);
    }

    private void userExistIdCheck(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ObjectNotFindException("Пользователь с ID: " + id + " не существует");
        }
    }
}
