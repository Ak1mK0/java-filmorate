package ru.yandex.practicum.filmorate.exception;

public class objectNotFindException extends RuntimeException {
    public objectNotFindException(String message) {
        super(message);
    }
}
