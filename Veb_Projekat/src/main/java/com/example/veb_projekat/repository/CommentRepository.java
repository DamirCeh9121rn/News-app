package com.example.veb_projekat.repository;

import com.example.veb_projekat.entities.Comment;

import java.util.List;

public interface CommentRepository {
    Comment insert(Comment comment);
    Comment update(Comment comment);
    Comment delete(Integer id);
    List<Comment> findByNewsId(Integer newsId);
    void addCommentLike(Integer commentID);
    void addCommentDislike(Integer commentID);
}
