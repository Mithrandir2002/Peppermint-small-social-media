package org.peppermint.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.model.Post;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.repository.UserRepository;
import org.peppermint.socialmedia.response.AuthResponse;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    UserService userService;
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/user/usersid")
    public ResponseEntity<Integer> getUserId(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return userService.findUserById(userId);
    }

    @PutMapping("/api/users/follow/{userId2}")
    public User followUser(@RequestHeader("Authorization") String jwt, @PathVariable Integer userId2) {
        User reqUser = userService.findUserByJwt(jwt);
        return userService.followUser(reqUser.getId(), userId2);
    }

    @GetMapping("api/users/follower/{userId}")
    public List<User> getFollowers(@PathVariable Integer userId) {
        return userService.getFollower(userId);
    }

    @GetMapping("api/users/following/{userId}")
    public List<User> getFollowings(@PathVariable Integer userId) {
        return userService.getFollowing(userId);
    }

    @GetMapping("/api/users/search")
    public List<User> searchUser(@RequestParam("query") String query) {
        return userService.searchUser(query);
    }

    @PostMapping("/api/users")
    public AuthResponse createUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PutMapping("/api/users")
    public User updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) {
        User reqUser = userService.findUserByJwt(jwt);
        return userService.updateUser(user, reqUser.getId());
    }

    @DeleteMapping("users/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId) {
        return "User deleted successfully";
    }

    @GetMapping("api/users/profile")
    public User getUserFromToken(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return user;
    }

    @GetMapping("/api/users/savedPost")
    public List<Post> getSavedPost(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return userService.getSavedPost(user.getId());  
    }
}
