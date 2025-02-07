package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Chat;
import org.peppermint.socialmedia.model.Message;
import org.peppermint.socialmedia.model.User;

import java.util.List;

public interface MessageService {
    public Message createMessage(User user, Integer chatId, Message message);
    public List<Message> findChatsMessages(Integer chatId);
}
