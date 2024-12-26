package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.exception.EntityNotFoundException;
import org.peppermint.socialmedia.model.Post;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserServiceImpl userService;
    @Override
    public Post createNewPost(Post post, Integer userId) {
        User user = userService.findUserById(userId);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (!post.getUser().getId().equals(userId)) return "Cannot delete post of another user!!!";
        else postRepository.delete(post);
        return "Sucessfully deleted post";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) {
        return unwrapPost(postId, postRepository.findById(postId));
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (!user.getSavedPost().contains(post)) user.getSavedPost().add(post);
        else user.getSavedPost().remove(post);
        userService.savedPost(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (!post.getLiked().contains(user)) post.getLiked().add(user);
        else post.getLiked().remove(user);
        return postRepository.save(post);
    }

    public Post unwrapPost(Integer postId, Optional<Post> optionalPost) {
        if (optionalPost.isPresent()) return optionalPost.get();
        else throw new EntityNotFoundException(postId, Post.class);
    }
}
