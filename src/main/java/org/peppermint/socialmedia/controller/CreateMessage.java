package org.peppermint.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.model.Chat;
import org.peppermint.socialmedia.model.Message;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.service.ChatService;
import org.peppermint.socialmedia.service.MessageService;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CreateMessage {
    private MessageService messageService;
    private UserService userService;
    private ChatService chatService;

    @PostMapping("/api/messages/chat/{chatId}")
    public Message createMessage(
            @RequestBody Message message,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer chatId) {
        User user = userService.findUserByJwt(jwt);
        Message createdMsg = messageService.createMessage(user, chatId, message);
        return createdMsg;
    }

    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public Message sendMessage(
            Message message,
            @DestinationVariable Integer chatId,
            @Header("simpSessionAttributes") Map<String, Object> sessionAttributes) {
        String token = (String) sessionAttributes.get("user");
        User user = userService.findUserByJwt(token);
        Chat chat = chatService.findChatWithMessagesById(chatId);
        Message createdMsg = messageService.createMessage(user, chatId, message);
        return createdMsg;
    }


    @GetMapping("/api/messages/chat/{chatId}")
    public List<Message> findChatsMessage(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId) {
        User user = userService.findUserByJwt(jwt);
        List<Message> messages = messageService.findChatsMessages(chatId);
        return messages;
    }
}
