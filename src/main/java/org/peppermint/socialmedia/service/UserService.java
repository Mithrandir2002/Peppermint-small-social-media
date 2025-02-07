package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Post;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.response.AuthResponse;

import java.util.List;

public interface UserService {
    public AuthResponse registerUser(User user);
    public User findUserById(Integer userId);
    public User findUserByEmail(String email);
    public User followUser(Integer userId1, Integer userId2);
    public User updateUser(User user, Integer userId);
    public List<User> searchUser(String query);
    public User savedPost(User user);
    User findUserByJwt(String jwt);
    public List<User> getFollower(Integer id);
    public List<User> getFollowing(Integer id);
    public List<Post> getSavedPost(Integer id);
}
