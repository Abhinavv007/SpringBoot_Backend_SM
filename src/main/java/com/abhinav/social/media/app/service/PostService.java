package com.abhinav.social.media.app.service;

import com.abhinav.social.media.app.models.Post;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.repository.PostRepository;
import com.abhinav.social.media.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createNewPost(Post post, Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            post.setUser(user);
            post.setCreatedAt(LocalDateTime.now());
            return postRepository.save(post);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public Post findPostById(Integer postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public String deletePostById(Integer postId, Integer userId) throws Exception {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            if (post.getUser().getId().equals(userId)) {
                postRepository.delete(post);
                return "Deleted Successfully";
            }
        }
        throw new Exception("Deletion Failed");
    }

    public List<Post> findPostByUserId(Integer userId) {
        return postRepository.findPostByUserId(userId);
    }

    public Post likePost(Integer postId, Integer userId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (postOpt.isPresent() && userOpt.isPresent()) {
            Post post = postOpt.get();
            User user = userOpt.get();

            if (post.getLiked().contains(user)) {
                post.getLiked().remove(user);
            } else {
                post.getLiked().add(user);
            }
            return postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Post or User not found");
        }
    }

    public Post savedPost(Integer postId, Integer userId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (postOpt.isPresent() && userOpt.isPresent()) {
            Post post = postOpt.get();
            User user = userOpt.get();

            if (user.getSavedPosts().contains(post)) {
                user.getSavedPosts().remove(post);
            } else {
                user.getSavedPosts().add(post);
            }
            userRepository.save(user);
            return post;
        } else {
            throw new IllegalArgumentException("Post or User not found");
        }
    }

    public void deleteAllByUser(User user){
        postRepository.deleteAllByUser(user);
    }
}
