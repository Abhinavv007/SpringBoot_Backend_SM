package com.abhinav.social.media.app.repository;

import com.abhinav.social.media.app.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {


}
