package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Chat;
import org.peppermint.socialmedia.model.User;

import java.util.List;

public interface ChatService {
    public Chat createChat(User reqUser, User user);
    public Chat findChatById(Integer chatId);
    public Chat findChatWithMessagesById(Integer chatId);
    public List<Chat> findUsersChat(Integer userId);
}
