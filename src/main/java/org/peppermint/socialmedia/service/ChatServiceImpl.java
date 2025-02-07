package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.exception.EntityNotFoundException;
import org.peppermint.socialmedia.model.Chat;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Override
    public Chat createChat(User reqUser, User user2) {
        Chat isExisted = chatRepository.findChatByUsersId(user2, reqUser);
        if (isExisted != null) return isExisted;
        Chat chat = new Chat();
        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);
        return chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(Integer chatId) {
        Chat chat = unwrapChat(chatRepository.findById(chatId), chatId);
        return chat;
    }

    @Override
    public Chat findChatWithMessagesById(Integer chatId) {
        Chat chat = unwrapChat(chatRepository.findChatWithMessagesById(chatId), chatId);
        return chat;
    }

    @Override
    public List<Chat> findUsersChat(Integer userId) {
        return chatRepository.findByUsersId(userId);
    }

    public Chat unwrapChat(Optional<Chat> optionalChat, Integer chatId) {
        if (optionalChat.isPresent()) return optionalChat.get();
        else throw new EntityNotFoundException(chatId, Chat.class);
    }
}
