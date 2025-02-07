package org.peppermint.socialmedia.controller;

import org.peppermint.socialmedia.model.Reels;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.service.ReelsService;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReelsController {
    @Autowired
    private ReelsService reelsService;
    private UserService userService;

    @PostMapping("/api/reels")
    public Reels createReels(@RequestBody Reels reels, @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        Reels createdReels = reelsService.createReel(reels, user);
        return createdReels;
    }

    @GetMapping("/api/reels")
    public List<Reels> findAllReels() {
        return reelsService.findAllReels();
    }

    @GetMapping("/api/reels/user/{userId}")
    public List<Reels> findUserReels(@PathVariable Integer userId) {
        List<Reels> reelsList = reelsService.findUsersReel(userId);
        return reelsList;
    }
}
