package com.example.newsServiceM4.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpsertNewsRequest {
    @NotNull(message = "Имя автора должно быть указано!")
    private String author;

    @NotNull(message = "Категория  должна быть указана!")
    private Long categoryId;

    private String title;

    private String newsText;


}
