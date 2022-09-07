package com.iyashasgowda.mortalenigma.services;

import com.iyashasgowda.mortalenigma.entities.Comment;
import com.iyashasgowda.mortalenigma.repositories.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private ICommentRepository iCommentRepository;

    public Comment saveComment(Comment comment) {
        iCommentRepository.incrementComments(comment.getMedia().getId());
        return iCommentRepository.save(comment);
    }

    public boolean removeComment(long media_id, long comment_id) {
        iCommentRepository.deleteById(comment_id);
        iCommentRepository.decrementComments(media_id);
        return true;
    }

    public List<Comment> getComments(long media_id) {
        return iCommentRepository.findByMedia_Id(media_id);
    }

    public List<Comment> getComments(long media_id, long user_id) {
        return iCommentRepository.findByMedia_IdAndUser_Id(media_id, user_id);
    }
}
