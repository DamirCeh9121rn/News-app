package com.example.veb_projekat.service;

import com.example.veb_projekat.entities.Comment;
import com.example.veb_projekat.repository.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {
    @Inject
    private CommentRepository commentRepository;

    public Comment insert(Comment comment) { return commentRepository.insert(comment); }
    public Comment update(Comment comment) { return commentRepository.update(comment); }
    public Comment delete(Integer id) { return commentRepository.delete(id); }
    public List<Comment> findAllByNewsId(Integer newsId) { return commentRepository.findByNewsId(newsId); }
    public void like(Integer commentID){commentRepository.addCommentLike(commentID);}
    public void dislike(Integer commentID){commentRepository.addCommentDislike(commentID);}

}
