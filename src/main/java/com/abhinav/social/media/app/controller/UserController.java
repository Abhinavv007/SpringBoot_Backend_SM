package com.abhinav.social.media.app.controller;


import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/user/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id){
        try{

           Optional<User> user=userService.findById(id);
           if(user.isPresent()) return new ResponseEntity<>(user.get(),HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List> getAllUsers(){
        try{
            List<User> userList=userService.getAllUsers();
            return new ResponseEntity<>(userList,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer userId){
        try{
            userService.deleteById(userId);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/user")
    public ResponseEntity<Void> updateUserById(@RequestBody User newData,@RequestHeader("Authorization") String jwt){
        try{
            User reqUser = userService.getUserByJwt(jwt);
            Boolean isUpdated = userService.updateUserById(reqUser.getId(),newData);
            if(isUpdated) return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping("/user/{userId2}")
    public ResponseEntity<User> followUserHandle(@RequestHeader("Authorization") String jwt,@PathVariable Integer userId2){

        try{

        User reqUser = userService.getUserByJwt(jwt);
        User user = userService.followUser(reqUser.getId(),userId2);
        return new ResponseEntity<>(user,HttpStatus.OK);

        } catch (Exception e) {
        throw new RuntimeException(e);
        }

    }

    @GetMapping("/search")
    public ResponseEntity<List> getByQuery(@RequestParam String query){
        try{
            List<User> userList = userService.getByQuery(query);
            if(!userList.isEmpty()) return new ResponseEntity<>(userList,HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/profile")
        public ResponseEntity<User> getUserFromToken(@RequestHeader("Authorization") String jwt){
        try{

         User user = userService.getUserByJwt(jwt);
            return new ResponseEntity<>(user,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
