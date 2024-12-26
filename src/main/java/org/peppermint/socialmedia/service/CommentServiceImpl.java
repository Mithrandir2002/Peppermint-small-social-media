package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.exception.EntityNotFoundException;
import org.peppermint.socialmedia.model.Comment;
import org.peppermint.socialmedia.model.Post;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.repository.CommentRepository;
import org.peppermint.socialmedia.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;
    UserService userService;
    PostService postService;
    PostRepository postRepository;
    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);
        comment.setUser(user);
        post.getComments().add(comment);
        postRepository.save(post);
        return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Integer commentId) {
        return unwrapComment(commentRepository.findById(commentId), commentId);
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);
        if (!comment.getLiked().contains(user)) comment.getLiked().add(user);
        else comment.getLiked().remove(user);
        return commentRepository.save(comment);
    }

    public Comment unwrapComment(Optional<Comment> optionalComment, Integer commentId) {
        if (optionalComment.isPresent()) return optionalComment.get();
        else throw new EntityNotFoundException(commentId, Comment.class);
    }
}
