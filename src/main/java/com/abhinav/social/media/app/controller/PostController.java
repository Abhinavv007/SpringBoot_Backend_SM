package com.abhinav.social.media.app.controller;

import com.abhinav.social.media.app.models.Post;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.service.PostService;
import com.abhinav.social.media.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestHeader("Authorization") String jwt){
        try{
            User reqUser = userService.getUserByJwt(jwt);
            Post newPost = postService.createNewPost(post, reqUser.getId());
            return new ResponseEntity<>(newPost,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Integer postId){
        try{
            Post post = postService.findPostById(postId);
            return new ResponseEntity<>(post,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId,@RequestHeader("Authorization") String jwt){
        try{
            User reqUser = userService.getUserByJwt(jwt);

            Post post = postService.findPostById(postId);
            if(post!=null){
            String message = postService.deletePostById(postId, reqUser.getId());
            return new ResponseEntity<>(message,HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/all")
    public ResponseEntity<List> getAllPosts(){
        try{
            List<Post> allPosts = postService.getAllPost();
            return new ResponseEntity<>(allPosts,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> findPostByUserId(@PathVariable Integer userId){
        try{
            List<Post> allPosts = postService.findPostByUserId(userId);
            return new ResponseEntity<>(allPosts,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/save/{postId}")
    public ResponseEntity<Post> savedPostHandler(@RequestHeader("Authorization") String jwt,@PathVariable Integer postId){
        try{
            User reqUser = userService.getUserByJwt(jwt);
            Post post = postService.savedPost(postId, reqUser.getId());
            return new ResponseEntity<>(post,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String jwt){
        try{
            User reqUser = userService.getUserByJwt(jwt);
            Post post = postService.likePost(postId, reqUser.getId());
            return new ResponseEntity<>(post,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
