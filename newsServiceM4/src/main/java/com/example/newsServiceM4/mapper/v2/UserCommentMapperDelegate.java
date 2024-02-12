package com.example.newsServiceM4.mapper.v2;

import com.example.newsServiceM4.model.UserComment;
import com.example.newsServiceM4.service.NewsService;
import com.example.newsServiceM4.service.UserService;
import com.example.newsServiceM4.web.model.UpsertUserCommentRequest;
import com.example.newsServiceM4.web.model.UserCommentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class UserCommentMapperDelegate implements CommentsMapper {

    @Autowired
    private UserService databaseUserService;

    @Autowired
    private NewsService databaseNewsService;

    @Autowired
    private UserMapperV2 userMapper;

    @Override
    public UserComment requestToUserComment(UpsertUserCommentRequest request) {
        UserComment userComment = new UserComment();
        userComment.setComment(request.getComment());
        userComment.setUser(databaseUserService.findById(request.getUserId()));
        userComment.setNews(databaseNewsService.findById(request.getNewsId()));

        return userComment;
    }

    @Override
    public UserComment requestToUserComment(Long userCommentId, UpsertUserCommentRequest request) {
        UserComment userComment = requestToUserComment(request);
        userComment.setId(userCommentId);

        return userComment;
    }

    @Override
    public UserCommentResponse userCommentToResponseForNews(UserComment userComment) {
        UserCommentResponse commentResponse = new UserCommentResponse();

        commentResponse.setComment(userComment.getComment());
        commentResponse.setId(userComment.getId());
        commentResponse.setUser(userMapper.userToResponse(userComment.getUser()));

        return commentResponse;
    }

}
