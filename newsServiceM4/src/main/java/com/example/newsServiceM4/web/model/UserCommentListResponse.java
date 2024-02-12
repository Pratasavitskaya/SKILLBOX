package com.example.newsServiceM4.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserCommentListResponse {

    private List<UserCommentResponse> userCommentsList = new ArrayList<>();


}
