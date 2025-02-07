package org.peppermint.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.model.Story;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.service.StoryService;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class StoryController {
    private StoryService storyService;
    private UserService userService;

    @PostMapping("/api/story")
    public Story createStory(@RequestBody Story story, @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        Story createdStory = storyService.createStory(story, user);
        return createdStory;
    }

    @PostMapping("/api/story/user/{userId}")
    public List<Story> findUsersStory(@RequestHeader("Authorization") String jwt, @PathVariable Integer userId) {
        User user = userService.findUserByJwt(jwt);
        return storyService.findStoryByUserId(userId);
    }
}
