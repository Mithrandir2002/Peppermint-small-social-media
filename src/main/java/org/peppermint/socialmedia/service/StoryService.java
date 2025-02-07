package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Story;
import org.peppermint.socialmedia.model.User;

import java.util.List;

public interface StoryService {
    public Story createStory(Story story, User user);
    public List<Story> findStoryByUserId(Integer userId);
}
