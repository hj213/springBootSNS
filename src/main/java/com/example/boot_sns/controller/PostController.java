package com.example.boot_sns.controller;

import com.example.boot_sns.model.Post;
import com.example.boot_sns.model.Member;
import com.example.boot_sns.service.LikeService;
import com.example.boot_sns.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final LikeService likeService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("post", new Post());
        return "post/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if(loginMember != null) {
            post.setAuthor(loginMember);
            postService.save(post);
        }
        return "redirect:/posts/create";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "post/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()) {
            return "redirect:/posts";
        }
        model.addAttribute("post", post.get());
        Member loginMember = (Member) session.getAttribute("loginMember");
        if(loginMember != null) {
            Boolean isLiked = likeService.findByPostIdAndMemberId(id,loginMember.getId()).isPresent();
            model.addAttribute("isLiked", isLiked);
        }
        return "post/view";
    }
}
