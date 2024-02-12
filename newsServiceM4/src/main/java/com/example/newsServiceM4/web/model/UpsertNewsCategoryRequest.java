package com.example.newsServiceM4.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertNewsCategoryRequest {
    @NotBlank(message = "Название категории должно быть заполнено!")
    private String category;
}
