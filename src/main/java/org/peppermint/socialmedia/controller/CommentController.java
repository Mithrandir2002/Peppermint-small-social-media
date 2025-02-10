package org.peppermint.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.model.Comment;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.service.CommentService;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
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

    @PutMapping("api/comments/{commentId}")
    public Comment updateComment(@RequestHeader("Authorization") String jwt, @RequestBody Comment comment, @PathVariable Integer commentId) {
        User user = userService.findUserByJwt(jwt);
        return commentService.updateComment(comment, commentId, user.getId());
    }

    @GetMapping("api/comments/{postId}")
    public ResponseEntity<Page<Comment>> getCommentsByPostId(@RequestHeader("Authorization") String jwt,
                                              @PathVariable("postId") Integer postId,
                                              @RequestParam(value = "offset", required = false) Integer offset,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                              @RequestParam(value = "sortBy", required = false) String sortBy) {
        if (offset == null) offset = 0;
        if (pageSize == null) pageSize = 20;
        if (sortBy.equals("")) sortBy = "id";
        return new ResponseEntity<>(commentService.pageableCommentbyPostId(postId, PageRequest.of(offset, pageSize, Sort.by(sortBy))), HttpStatus.OK);
    }
}
