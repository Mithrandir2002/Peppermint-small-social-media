package org.peppermint.socialmedia.repository;

import org.peppermint.socialmedia.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
