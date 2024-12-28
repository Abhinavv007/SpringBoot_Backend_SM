package com.abhinav.social.media.app.controller;

import com.abhinav.social.media.app.models.Message;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.service.MessageService;
import com.abhinav.social.media.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/message/{chatId}")
    public ResponseEntity<Message> createMessage(@RequestHeader("Authorization") String jwt,@RequestBody Message req ,@PathVariable Integer chatId){
        try{
            User reqUser = userService.getUserByJwt(jwt);
            Message message = messageService.createMessage(reqUser.getId(),chatId,req);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/message/{chatId}")
    public ResponseEntity<List<Message>> findChatsMessages(@RequestHeader("Authorization") String jwt,@PathVariable Integer chatId){
        try{

            List<Message> messages = messageService.findChatsMessages(chatId);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
