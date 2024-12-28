package com.abhinav.social.media.app.service;

import com.abhinav.social.media.app.models.Comment;
import com.abhinav.social.media.app.models.Post;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.repository.CommentRepository;
import com.abhinav.social.media.app.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    public Comment createComment(Comment comment,Integer postId, Integer userId) throws Exception{
        User user = userService.getUserById(userId);
        Post post = postService.findPostById(postId);
        if(user!=null && post!=null){

        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);
        postRepository.save(post);
        return savedComment;
        } else{
            throw new Exception("User or Post doesn't exists.");
        }
    }

    public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> comment =commentRepository.findById(commentId);
        if(comment.isEmpty()) throw new Exception("Comment doesn't exists.");
        return comment.get();
    }

    public Comment likeComment(Integer commentId, Integer userId){
        User user = userService.getUserById(userId);
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(!comment.get().getLiked().contains(user)){
            comment.get().getLiked().add(user);
        } else{
            comment.get().getLiked().remove(user);

        }
        return commentRepository.save(comment.get());
    }


}
