package com.example.newsServiceM4.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class NewsCategoryListResponse {

    private List<NewsCategoryResponse> newsCategoryList = new ArrayList<>();

}
