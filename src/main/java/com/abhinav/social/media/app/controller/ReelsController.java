package com.abhinav.social.media.app.controller;

import com.abhinav.social.media.app.models.Reels;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.service.ReelService;
import com.abhinav.social.media.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReelsController {

    @Autowired
    private ReelService reelService;

    @Autowired
    private UserService userService;

    @PostMapping("/reels")
    public ResponseEntity<Reels> createReel(@RequestBody Reels reel , @RequestHeader("Authorization") String jwt) throws Exception {
    try{
        User reqUser = userService.getUserByJwt(jwt);
        Reels saveReel = reelService.createReel(reel, reqUser.getId());
        return new ResponseEntity<>(saveReel,HttpStatus.OK);
    } catch (Exception e){
      throw new Exception(e);
    }
    }

    @GetMapping("/reels")
    public ResponseEntity<List<Reels>> getAllReels(){
        try{
            List<Reels> listOfReels = reelService.getAllReels();
            return new ResponseEntity<>(listOfReels,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reels/user/{userId}")
    public ResponseEntity<List<Reels>> getReelsOfUser(@PathVariable Integer userId){
        try{
            List<Reels> listOfReels = reelService.findUsersReelById(userId);
            return new ResponseEntity<>(listOfReels,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
