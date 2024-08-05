package com.example.boot_sns.controller;

import com.example.boot_sns.model.Follow;
import com.example.boot_sns.model.Member;
import com.example.boot_sns.service.FollowService;
import com.example.boot_sns.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;
    private final MemberService memberService;

    @PostMapping("/toggle/{followingId}")
    public  String toggle(@PathVariable Long followingId, HttpSession session){
        Member loginMember = (Member) session.getAttribute("loginMember");
        if(loginMember != null &&  !loginMember.getId().equals(followingId)){
            Follow oldFollow = followService.findByFollowerIdAndFollowingId(loginMember.getId(), followingId).orElse(null);
            if(oldFollow != null){
                followService.deleteById(oldFollow.getId());
            } else {
                Follow follow = new Follow();
                follow.setFollowing(memberService.findById(followingId).get());
                follow.setFollower(loginMember);
                followService.save(follow);
            }
        }
        return "redirect:/members/"+followingId;
    }
}
