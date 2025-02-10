package org.peppermint.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.model.Post;
import org.peppermint.socialmedia.model.PostDTO;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.response.ResponseMessage;
import org.peppermint.socialmedia.service.PostService;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    public ResponseEntity<PostDTO> findPostById(@PathVariable("postId") Integer postId) {
        return new ResponseEntity<>(postService.findPostDTOById(postId), HttpStatus.OK);
    }

    @GetMapping("/api/posts/user/{userId}")
    public ResponseEntity<Page<PostDTO>> findUserPosts(@PathVariable Integer userId,
                                                    @RequestParam(value = "offset", required = false) Integer offset,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", required = false) String sortBy) {
        if (offset == null) offset = 0;
        if (pageSize == null) offset = 0;
        if (sortBy.equals("")) sortBy = "id";
        return new ResponseEntity<>(postService.findPostByIdPage(userId, PageRequest.of(offset, pageSize, Sort.by(sortBy))), HttpStatus.OK);
    }

//    @GetMapping("/api/posts")
//    public ResponseEntity<List<Post>> findAllPosts() {
//        return new ResponseEntity<>(postService.findAllPost(), HttpStatus.OK);
//    }

    @GetMapping("/api/posts")
    public Page<PostDTO> getPosts(@RequestParam(value = "offset", required = false) Integer offset,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                  @RequestParam(value = "sortBy", required = false) String sortBy) {
        if (offset == null) offset = 0;
        if (pageSize == null) pageSize = 0;
        if (sortBy.equals("")) sortBy = "id";
        return postService.getPostsPage(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
    }

    @PutMapping("/api/posts/save/{postId}")
    public ResponseEntity<Post> savePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>(postService.savedPost(postId, user.getId()), HttpStatus.OK);
    }

    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<Post> likePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>(postService.likePost(postId, user.getId()), HttpStatus.OK);
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<Post> updatePost(@RequestHeader("Authorization") String jwt, @RequestBody Post post, @PathVariable Integer postId) {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>(postService.updatePost(post, postId, user.getId()), HttpStatus.OK);
    }
}
