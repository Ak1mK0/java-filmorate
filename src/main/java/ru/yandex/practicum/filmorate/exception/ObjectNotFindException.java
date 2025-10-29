package ru.yandex.practicum.filmorate.exception;

public class ObjectNotFindException extends RuntimeException {
    public ObjectNotFindException(String message) {
        super(message);
    }
}
