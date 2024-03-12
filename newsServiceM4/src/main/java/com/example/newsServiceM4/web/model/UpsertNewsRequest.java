package com.example.newsServiceM4.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertNewsRequest {

    private String author;

    @NotNull(message = "Категория  должна быть указана!")
    private Long categoryId;

    private String title;

    private String newsText;


}
