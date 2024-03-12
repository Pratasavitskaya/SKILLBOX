package com.example.newsServiceM4.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserRequest {

    @NotBlank(message = "Имя пользователя должно быть заполнено!")
    @Size(min = 3, max = 15, message = "Имя пользователя не может быть меньше {min}  и больше {max}!")
    private String name;

}
