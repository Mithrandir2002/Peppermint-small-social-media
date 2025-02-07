package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Reels;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.repository.ReelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelsServiceImpl implements ReelsService{

    @Autowired
    private ReelsRepository reelsRepository;
    private UserService userService;
    @Override
    public Reels createReel(Reels reel, User user) {
        Reels createdReel = new Reels();
        createdReel.setTitle(reel.getTitle());
        createdReel.setVideo(reel.getVideo());
        createdReel.setUser(user);
        reelsRepository.save(createdReel);
        return createdReel;
    }

    @Override
    public List<Reels> findAllReels() {
        return reelsRepository.findAll();
    }

    @Override
    public List<Reels> findUsersReel(Integer userId) {
        User user = userService.findUserById(userId);
        return reelsRepository.findByUserId(userId);
    }
}
