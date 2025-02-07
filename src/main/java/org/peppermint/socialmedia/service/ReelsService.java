package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Reels;
import org.peppermint.socialmedia.model.User;

import java.util.List;

public interface ReelsService {
    public Reels createReel(Reels reel, User user);
    public List<Reels> findAllReels();
    public List<Reels> findUsersReel(Integer userId);
}
