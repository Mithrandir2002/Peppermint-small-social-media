package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Post;
import org.peppermint.socialmedia.model.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Post createNewPost(Post post, Integer userId);
    String deletePost(Integer postId, Integer userId);
    List<Post> findPostByUserId(Integer userId);

    Post findPostById(Integer postId);
    List<Post> findAllPost();
    Post savedPost(Integer postId, Integer userId);
    Post likePost(Integer postId, Integer userId);
    Post updatePost(Post post, Integer postId, Integer userId);

    Page<PostDTO> getPostsPage(PageRequest of);
    Page<PostDTO> findPostByIdPage(Integer userId, PageRequest of);
    public PostDTO findPostDTOById(Integer postId);
}
