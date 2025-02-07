package org.peppermint.socialmedia.service;

import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.model.Story;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StoryServiceImpl implements StoryService{
    private StoryRepository storyRepository;
    private UserService userService;

    @Override
    public Story createStory(Story story, User user) {
        Story createdStory = new Story();
        createdStory.setCaption(story.getCaption());
        createdStory.setUser(user);
        createdStory.setImage(story.getImage());
        return storyRepository.save(createdStory);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) {
        User user = userService.findUserById(userId);
        return storyRepository.findByUserId(userId);
    }
}
