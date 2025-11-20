package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.annotation.ValidateDate;
import ru.yandex.practicum.filmorate.annotation.ValidateNoBlank;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull(message = "Адрес пользователя не может быть пустым")
    @Email(message = "Не корректный формат электронная почты")
    private String email;
    @NotNull(message = "Логин пользователя не может быть пустым")
    @ValidateNoBlank(message = "Логин пользователя не должен содержит пробелы")
    private String login;
    @ValidateDate(
            beforeOrAfter = ValidateDate.BeforeOrAfter.isAfter,
            message = "Дата не может быть в будущем")
    private LocalDate birthday;
    private String name;

}
