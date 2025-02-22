package org.peppermint.socialmedia.repository;

import org.peppermint.socialmedia.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("select p from Post p where p.user.id = :userId")
    Page<Post> findPostByUserId(@Param("userId") Integer userId, Pageable pageable);
}
