package org.peppermint.socialmedia.service;

import org.peppermint.socialmedia.model.Chat;
import org.peppermint.socialmedia.model.Message;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.repository.ChatRepository;
import org.peppermint.socialmedia.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRepository chatRepository;
    @Override
    public Message createMessage(User user, Integer chatId, Message message) {
        Chat chat = chatService.findChatWithMessagesById(chatId);
        Message createdMsg = Message.builder()
                .chat(chat)
                .content(message.getContent())
                .image(message.getImage())
                .user(message.getUser())
                .build();
        messageRepository.save(createdMsg);
        chat.getMessages().add(createdMsg);
        chatRepository.save(chat);
        return createdMsg;
    }

    @Override
    public List<Message> findChatsMessages(Integer chatId) {
        return messageRepository.findByChatId(chatId);
    }
}
