package com.example.newsServiceM4.mapper.v2;

import com.example.newsServiceM4.model.UserComment;
import com.example.newsServiceM4.web.model.*;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(UserCommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapperV2.class})
public interface CommentsMapper {


    UserComment requestToUserComment(UpsertUserCommentRequest request);

    @Mapping(source = "commentId", target = "id")
    UserComment requestToUserComment(Long commentId, UpsertUserCommentRequest request);

    UserCommentResponse userCommentToResponse(UserComment userComment);

    UserCommentResponse userCommentToResponseForNews(UserComment userComment);


    default UserCommentListResponse userCommentListToUserCommentResponseList(List<UserComment> userComments) {
        UserCommentListResponse response = new UserCommentListResponse();

        response.setUserCommentsList(userComments.stream()
                .map(this::userCommentToResponse).collect(Collectors.toList()));

        return response;
    }

    default UserCommentListResponse userCommentListToUserCommentResponseListForNews(List<UserComment> userComments) {
        UserCommentListResponse response = new UserCommentListResponse();

        response.setUserCommentsList(userComments.stream()
                .map(this::userCommentToResponseForNews).collect(Collectors.toList()));

        return response;
    }

}
