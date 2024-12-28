package com.abhinav.social.media.app.repository;

import com.abhinav.social.media.app.models.Reels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelsRepository  extends JpaRepository<Reels,Integer> {
    List<Reels> findByUserId(Integer userId);
}
