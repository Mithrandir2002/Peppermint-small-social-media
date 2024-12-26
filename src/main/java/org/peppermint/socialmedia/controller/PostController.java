package org.peppermint.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.model.Post;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.response.ResponseMessage;
import org.peppermint.socialmedia.service.PostService;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class PostController {
    @Autowired
    PostService postService;
    UserService userService;

    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>(postService.createNewPost(post, user.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<ResponseMessage> deletePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId, user.getId());
        ResponseMessage responseMessage = new ResponseMessage(message, true);
        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable("posId") Integer postId) {
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/api/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUserPosts(@PathVariable Integer userId) {
        return new ResponseEntity<>(postService.findPostByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> findAllPosts() {
        return new ResponseEntity<>(postService.findAllPost(), HttpStatus.OK);
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<Post> savePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>(postService.savedPost(postId, user.getId()), HttpStatus.OK);
    }

    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<Post> likePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>(postService.likePost(postId, user.getId()), HttpStatus.OK);
    }
}
