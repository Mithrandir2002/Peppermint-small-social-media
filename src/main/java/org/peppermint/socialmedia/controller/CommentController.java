package org.peppermint.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.model.Comment;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.service.CommentService;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CommentController {
    @Autowired
    private CommentService commentService;
    private UserService userService;

    @PostMapping("/api/comments/post/{postId}")
    public Comment createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String jwt, @PathVariable("postId") Integer postId) {
        User user = userService.findUserByJwt(jwt);
        Comment createdComment= commentService.createComment(comment, postId, user.getId());
        return createdComment;
    }

    @PutMapping("/api/comments/like/{commentId}")
    public Comment likeComment(@RequestHeader("Authorization") String jwt, @PathVariable("commentId") Integer commentId) {
        User user = userService.findUserByJwt(jwt);
        Comment likeComment = commentService.likeComment(commentId, user.getId());
        return likeComment;
    }
}
