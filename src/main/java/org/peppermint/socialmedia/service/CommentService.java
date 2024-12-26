package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Comment;

public interface CommentService {
    public Comment createComment(Comment comment, Integer postId, Integer userId);
    public Comment findCommentById(Integer commentId);
    public Comment likeComment(Integer commentId, Integer userId);
}
