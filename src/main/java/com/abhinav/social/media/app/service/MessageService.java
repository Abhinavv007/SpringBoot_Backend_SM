package com.abhinav.social.media.app.service;

import com.abhinav.social.media.app.models.Chat;
import com.abhinav.social.media.app.models.Message;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.repository.ChatRepository;
import com.abhinav.social.media.app.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    public Message createMessage(Integer userId, Integer chatId, Message req){
        Chat chat = chatService.findChatById(chatId);
        Message message = new Message();

        message.setImage(req.getImage());
        message.setChat(chat);
        message.setContent(req.getContent());
        message.setUser(userService.getUserById(userId));
        message.setTimestamp(LocalDateTime.now());
        Message saved= messageRepository.save(message);
        chat.getMessages().add(saved);
        chatRepository.save(chat);
        return saved;

    }

    public List<Message> findChatsMessages(Integer chatId){

        return messageRepository.findByChatId(chatId);
    }

}
