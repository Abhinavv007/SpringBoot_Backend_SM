package com.abhinav.social.media.app.service;

import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.repository.UserRepository;
import com.abhinav.social.media.app.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    public User createUser(User newUser){
        return userRepository.save(newUser);

    }
    public Optional<User> findById(Integer id){
        return userRepository.findById(id);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public void deleteById(Integer id){
        Optional<User> userOpt = userRepository.findById(id);

        if(userOpt.isPresent()){
            User user = userOpt.get();
            postService.deleteAllByUser(user);
            userRepository.delete(user);
        } else{
            throw new IllegalArgumentException("User not found");
        }

    }

    public boolean updateUserById(Integer id, User newData){
        User user = userRepository.findById(id).orElse(null);
        if(user!=null){
            user.setFirstName(newData.getFirstName());
            user.setLastName(newData.getLastName());
            user.setEmail(newData.getEmail());
            user.setPassword(newData.getPassword());
            userRepository.save(user);
            return true;
        } else{
            return false;
        }
    }

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    public User followUser(Integer reqUser, Integer userId2) {
        Optional<User> user1Opt = userRepository.findById(reqUser);
        Optional<User> user2Opt = userRepository.findById(userId2);

        if (user1Opt.isPresent() && user2Opt.isPresent()) {
            User user1 = user1Opt.get();
            User user2 = user2Opt.get();
            if(user2.getFollowers().contains(user1.getId())){
                user1.getFollowings().remove(user2.getId());
                user2.getFollowers().remove(user1.getId());
            } else{

            user2.getFollowers().add(user1.getId());
            user1.getFollowings().add(user2.getId());
            }

            userRepository.save(user1);
            userRepository.save(user2);

            return user1;
        } else {
            throw new IllegalArgumentException("One or both users not found");
        }
    }

    public List<User> getByQuery(String query){
        List<User> matchingList = userRepository.searchUser(query);
        if(!matchingList.isEmpty()) return matchingList;
        return new ArrayList<>();


    }

    public User getUserById(Integer userId){
        return userRepository.getUserById(userId);
    }

    public User getUserByJwt(String jwt){
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.getUserByEmail(email);
        return user;
    }



}
