package org.peppermint.socialmedia.repository;

import org.peppermint.socialmedia.model.Story;
import org.peppermint.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {
    public List<Story> findByUserId(Integer userId);
}
