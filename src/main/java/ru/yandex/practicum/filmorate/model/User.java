package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.annotation.ValidateDate;
import ru.yandex.practicum.filmorate.annotation.ValidateDate.BeforeOrAfter;
import ru.yandex.practicum.filmorate.annotation.ValidateNoBlank;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {
    private Long id;
    @NotNull(message = "Адрес пользователя не может быть пустым")
    @Email(message = "Не корректный формат электронная почты")
    private String email;
    @NotNull(message = "Логин пользователя не может быть пустым")
    @ValidateNoBlank(message = "Логин пользователя не должен содержит пробелы")
    private String login;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ValidateDate(
            beforeOrAfter = BeforeOrAfter.isAfter,
            message = "Дата не может быть в будущем")
    private LocalDate birthday;
    private Set<Long> friends;
}
