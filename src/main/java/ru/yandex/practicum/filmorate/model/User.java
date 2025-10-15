package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private Long id;
    //    @NonNull
//    @NotBlank
//    @Email (message = "Не корректная электронная почта")
    private String email;
    private String login;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
