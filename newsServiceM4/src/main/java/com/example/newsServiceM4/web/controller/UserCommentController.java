package com.example.newsServiceM4.web.controller;

import com.example.newsServiceM4.mapper.v2.CommentsMapper;
import com.example.newsServiceM4.model.UserComment;
import com.example.newsServiceM4.service.CommentService;
import com.example.newsServiceM4.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/comments")
public class UserCommentController {

    private final CommentService databaseCommentService;

    private final CommentsMapper commentsMapper;

    @GetMapping
    public ResponseEntity<UserCommentListResponse> findAll() {
        return ResponseEntity.ok(
                commentsMapper.userCommentListToUserCommentResponseList(
                        databaseCommentService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                commentsMapper.userCommentToResponse(databaseCommentService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<UserCommentResponse> create(@RequestBody @Valid UpsertUserCommentRequest request) {
        UserComment newUserComment = databaseCommentService.save(commentsMapper.requestToUserComment(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentsMapper.userCommentToResponse(newUserComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserCommentResponse> update(@PathVariable("id") Long commentId,
                                                      @RequestBody @Valid UpsertUserCommentRequest request) {
        UserComment updatedComment = databaseCommentService.update(commentsMapper.requestToUserComment(commentId, request));

        return ResponseEntity.ok(commentsMapper.userCommentToResponse(updatedComment));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        databaseCommentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
