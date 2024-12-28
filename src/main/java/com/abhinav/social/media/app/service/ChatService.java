package com.abhinav.social.media.app.service;

import com.abhinav.social.media.app.models.Chat;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat createChat(User reqUser, User user2){
        Chat isExist = chatRepository.findChatByUserId(user2,reqUser);
        if(isExist!=null){
            return isExist;
        }
        Chat chat = new Chat();
        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);
        chat.setTimeStamp(LocalDateTime.now());
        return chatRepository.save(chat);


    }

    public Chat findChatById(Integer id){
        Optional<Chat> chat = chatRepository.findById(id);
        return chat.get() ;
    }

    public List<Chat> findUsersChat(Integer userId){

        return chatRepository.findByUsersId(userId);
    }


}
