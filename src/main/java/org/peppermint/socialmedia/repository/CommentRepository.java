package org.peppermint.socialmedia.repository;

import org.peppermint.socialmedia.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("select c from Comment c where c.post.id = :postId")
    Page<Comment> getCommentFromPostId(@Param("postId") Integer postId, Pageable of);
}
