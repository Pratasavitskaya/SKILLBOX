package com.example.newsServiceM4.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertNewsCategoryRequest {
    @NotBlank(message = "Название категории должно быть заполнено!")
    private String category;
}
