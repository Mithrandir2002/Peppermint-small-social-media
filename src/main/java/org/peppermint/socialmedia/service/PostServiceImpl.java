package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.exception.EntityNotFoundException;
import org.peppermint.socialmedia.exception.UnauthorizedException;
import org.peppermint.socialmedia.model.Post;
import org.peppermint.socialmedia.model.PostDTO;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    BaseRedisService redisService;
    private static final String POST_CACHE_PREFIX = "post:";
    @Override
    public Post createNewPost(Post post, Integer userId) {
        User user = userService.findUserById(userId);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) {
        String cacheKey = POST_CACHE_PREFIX + postId;
        Post post = unwrapPost(postId, postRepository.findById(postId));
        User user = userService.findUserById(userId);
        if (post.getUser().getId() != userId) {
            throw new UnauthorizedException("User does not own this post.");
        } else {
            postRepository.delete(post);
//            redisService.delete(cacheKey);
            return "Sucessfully deleted post";
        }
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) {
//        String cacheKey = POST_CACHE_PREFIX + postId;
//        Post cachedPost = (Post) redisService.get(cacheKey);
//        if (cachedPost != null) return cachedPost;
        Post post = unwrapPost(postId, postRepository.findById(postId));
//        redisService.set(cacheKey, post);
//        redisService.setTimeToLive(cacheKey, 1);
        System.out.println(post.getComments().size());
        return post;
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) {
        Post post = unwrapPost(postId, postRepository.findById(postId));
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

    @Override
    public Post updatePost(Post post, Integer postId, Integer userId) {
        User user = userService.findUserById(userId);
        Post updatedPost = findPostById(postId);
        if (!updatedPost.getUser().equals(user)) throw new RuntimeException();
        if (post.getCaption() != null) updatedPost.setCaption(post.getCaption());
        if (post.getImage() != null) updatedPost.setImage(post.getImage());
        if (post.getVideo() != null) updatedPost.setVideo(post.getVideo());

        Post savedPost = postRepository.save(updatedPost);

//        String cacheKey = POST_CACHE_PREFIX + postId;
//        redisService.set(cacheKey, updatedPost);
//        redisService.setTimeToLive(cacheKey, 1);

        return savedPost;
    }

    @Override
    public Page<PostDTO> getPostsPage(PageRequest of) {
        Page<Post> posts = postRepository.findAll(of);
        return posts.map(post -> convertToPostDTO(post));
    }

    public Post unwrapPost(Integer postId, Optional<Post> optionalPost) {
        if (optionalPost.isPresent()) return optionalPost.get();
        else throw new EntityNotFoundException(postId, Post.class);
    }

    public PostDTO convertToPostDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .image(post.getImage())
                .likeCount(post.getLiked().size())
                .commentCount(post.getComments().size())
                .owner(post.getUser().getFirstName() + " " + post.getUser().getLastName())
                .build();
    }
}
