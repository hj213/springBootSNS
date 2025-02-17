package com.example.boot_sns.service;

import com.example.boot_sns.model.Follow;
import com.example.boot_sns.model.Like;
import com.example.boot_sns.repository.FollowRepository;
import com.example.boot_sns.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    public Follow save(Follow follow) {
        return followRepository.save(follow);
    }

    public Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId) {
        return followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
    }

    public void deleteById(Long id) {
        followRepository.deleteById(id);
    }
}
