package com.example.newsServiceM4.web.model;

import jakarta.persistence.NamedAttributeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCommentListResponse {

    private List<UserCommentResponse> userCommentsList = new ArrayList<>();


}
