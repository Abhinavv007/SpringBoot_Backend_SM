package com.abhinav.social.media.app.service;

import com.abhinav.social.media.app.models.Reels;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.repository.ReelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReelService {

    @Autowired
    private ReelsRepository reelsRepository;

    @Autowired
    private UserService userService;


    public Reels createReel(Reels reel, Integer userId) throws Exception{
        Optional<User> user = userService.findById(userId);
        if(!user.isEmpty()){
            reel.setUser(user.get());
            return reelsRepository.save(reel);
        }
            throw new Exception("User not found");
    }

    public List<Reels> findUsersReelById(Integer userId) throws Exception{
        Optional<User> user = userService.findById(userId);
        if(!user.isEmpty()) return reelsRepository.findByUserId(userId);
        throw new Exception("User not found");

    }

    public List<Reels> getAllReels(){
    return reelsRepository.findAll();
    }

}
