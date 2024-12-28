package com.abhinav.social.media.app.controller;

import com.abhinav.social.media.app.models.Comment;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.service.CommentService;
import com.abhinav.social.media.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/api/{postId}")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String jwt,@PathVariable Integer postId){
        try {
        User reqUser = userService.getUserByJwt(jwt);
            Comment comm = commentService.createComment(comment,postId,reqUser.getId());
            return new ResponseEntity<>(comm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping("/api/{commentId}")
    public ResponseEntity<Comment> likeComment( @RequestHeader("Authorization") String jwt,@PathVariable Integer commentId){
        try {
            User reqUser = userService.getUserByJwt(jwt);
            Comment comm = commentService.likeComment(commentId,reqUser.getId());
            return new ResponseEntity<>(comm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
}
