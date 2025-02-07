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
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);
        postRepository.save(post);
        return savedComment;
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

    @Override
    public Comment updateComment(Comment comment, Integer cmtId, Integer userId) {
        User user = userService.findUserById(userId);
        Comment updatedComment = findCommentById(cmtId);
        if (!user.getComments().contains(updatedComment)) throw new RuntimeException();
        if (comment.getContent() != null) updatedComment.setContent(comment.getContent());
        return commentRepository.save(updatedComment);
    }

    public Comment unwrapComment(Optional<Comment> optionalComment, Integer commentId) {
        if (optionalComment.isPresent()) return optionalComment.get();
        else throw new EntityNotFoundException(commentId, Comment.class);
    }
}
