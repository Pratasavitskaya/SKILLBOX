package com.example.newsServiceM4.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsListResponse {

    private List<NewsResponseForList> newsList = new ArrayList<>();
}
