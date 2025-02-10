package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    public Comment createComment(Comment comment, Integer postId, Integer userId);
    public Comment findCommentById(Integer commentId);
    public Comment likeComment(Integer commentId, Integer userId);
    public Comment updateComment(Comment comment, Integer cmtId, Integer userId);
    public Page<Comment> pageableCommentbyPostId(Integer postId, Pageable of);

}
