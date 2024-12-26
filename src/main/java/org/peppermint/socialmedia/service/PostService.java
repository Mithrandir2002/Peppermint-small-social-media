package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Post;

import java.util.List;

public interface PostService {
    Post createNewPost(Post post, Integer userId);
    String deletePost(Integer postId, Integer userId);
    List<Post> findPostByUserId(Integer userId);
    Post findPostById(Integer postId);
    List<Post> findAllPost();
    Post savedPost(Integer postId, Integer userId);
    Post likePost(Integer postId, Integer userId);
}
