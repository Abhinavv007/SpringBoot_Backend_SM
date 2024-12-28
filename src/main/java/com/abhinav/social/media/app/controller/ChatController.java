package com.abhinav.social.media.app.controller;

import com.abhinav.social.media.app.models.Chat;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.service.ChatService;
import com.abhinav.social.media.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("/chats")
    public ResponseEntity<Chat> createChat(@RequestBody CreateChatRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User reqUser = userService.getUserByJwt(jwt);
            Optional<User> user2 = userService.findById(req.getUser2Id());
            if (user2.isPresent()) {
                Chat chat = chatService.createChat(reqUser, user2.get());
                return new ResponseEntity<>(chat, HttpStatus.OK);
            } else {
                throw new Exception("User2 not found");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @GetMapping("/chat")
    public ResponseEntity<List<Chat>> findUsersChat(@RequestHeader("Authorization") String jwt) {
        try {
            User reqUser = userService.getUserByJwt(jwt);
            List<Chat> chat = chatService.findUsersChat(reqUser.getId());
            if (!chat.isEmpty()) return new ResponseEntity<>(chat, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<Chat> findChatById(@PathVariable Integer chatId) {
        try {
            Chat chat = chatService.findChatById(chatId);
            if (chat != null) return new ResponseEntity<>(chat, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
