package com.abhinav.social.media.app.repository;

import com.abhinav.social.media.app.models.Post;
import com.abhinav.social.media.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query("select p from Post p where p.user.id=:userId")
    List<Post> findPostByUserId(Integer userId);

    @Modifying
    @Transactional
    @Query("delete from Post p where p.user= :user")
    void deleteAllByUser(@Param("user") User user);


}
