package com.example.boot_sns.repository;

import com.example.boot_sns.model.Comment;
import com.example.boot_sns.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostIdAndMemberId(Long postId, Long memberId);
}
