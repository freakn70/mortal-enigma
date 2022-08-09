package com.iyashasgowda.yservice.services;

import com.iyashasgowda.yservice.entities.Comment;
import com.iyashasgowda.yservice.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    public Comment saveComment(Comment comment) {
        return repository.save(comment);
    }

    public List<Comment> getComments(long media_id) {
        return repository.findByMedia_Id(media_id);
    }

    public List<Comment> getComments(long media_id, long user_id) {
        return repository.findByMedia_IdAndUser_Id(media_id, user_id);
    }
}
