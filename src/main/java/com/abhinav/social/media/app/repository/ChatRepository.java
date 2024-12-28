package com.abhinav.social.media.app.repository;

import com.abhinav.social.media.app.models.Chat;
import com.abhinav.social.media.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ChatRepository extends JpaRepository<Chat,Integer> {
    @Query("select c from Chat c where :user member of c.users and :reqUser member of c.users")
    Chat findChatByUserId(@Param("user")User user, @Param("reqUser") User reqUser);
    List<Chat> findByUsersId(Integer userId);
}
