package com.example.newsServiceM4.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCategoryResponse {

    private Long id;

    private String category;


}
