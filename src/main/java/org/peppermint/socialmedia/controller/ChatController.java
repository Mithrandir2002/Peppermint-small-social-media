package org.peppermint.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.model.Chat;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.request.CreateChatRequest;
import org.peppermint.socialmedia.securityconfig.SecurityConstants;
import org.peppermint.socialmedia.service.ChatService;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ChatController {
    @Autowired
    private ChatService chatService;
    private UserService userService;
    @PostMapping("/api/chats")
    public Chat createChat(@RequestHeader("Authorization") String jwt, @RequestBody CreateChatRequest chatRequest) {
        User reqUser = userService.findUserByJwt(jwt);
        User user = userService.findUserById(chatRequest.getUserId());
        Chat chat = chatService.createChat(reqUser, user);
        return chat;
    }

    @GetMapping("/api/chats")
    public List<Chat> findUserChats(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        List<Chat> chats = chatService.findUsersChat(user.getId());
        return chats;
    }
}
