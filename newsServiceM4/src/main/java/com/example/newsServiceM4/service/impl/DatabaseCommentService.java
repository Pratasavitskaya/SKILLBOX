package com.example.newsServiceM4.service.impl;

import com.example.newsServiceM4.aop.Loggable;
import com.example.newsServiceM4.exception.EntityNotFoundException;
import com.example.newsServiceM4.model.UserComment;
import com.example.newsServiceM4.repository.DatabaseCommentRepository;
import com.example.newsServiceM4.service.CommentService;
import com.example.newsServiceM4.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCommentService implements CommentService {

    private final DatabaseCommentRepository commentRepository;

    @Override
    public List<UserComment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public UserComment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        (MessageFormat.format("Комментарий с ID: {0} не найден!", id)));
    }

    @Override
    public UserComment save(UserComment userComment) {
        return commentRepository.save(userComment);
    }

    @Override
    @Loggable
    public UserComment update(UserComment userComment) {
        UserComment existedUserComment = findById(userComment.getId());
        BeanUtils.copyNonNullProperties(userComment, existedUserComment);
        return commentRepository.save(userComment);

    }

    @Override
    // @Loggable
    @Transactional
    public void deleteById(Long id) {

        commentRepository.deleteById(id);

    }

    @Override
    public void deleteByIdIn(List<Long> ids) {

        commentRepository.deleteAll();

    }
}
