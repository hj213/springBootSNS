package com.example.boot_sns.controller;

import com.example.boot_sns.model.Comment;
import com.example.boot_sns.model.Member;
import com.example.boot_sns.model.Post;
import com.example.boot_sns.service.CommentService;
import com.example.boot_sns.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @GetMapping("/post/{postId}")
    public String commentList(@PathVariable Long postId, Model model) {
        Optional<Post> post = postService.findById(postId);
        model.addAttribute("comments",post.get().getComments());
        model.addAttribute("postId", postId);
        return "comment/list";
    }

    @PostMapping("/create")
    public String createComment(@ModelAttribute Comment comment, @RequestParam Long postId, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if(loginMember != null) {
            comment.setMember(loginMember);
            Optional<Post> post = Optional.of(postService.findById(postId).get());
            if(post.isEmpty()) {
                return "redirect:/posts/" ;
            }
            comment.setPost(postService.findById(postId).get());
            commentService.save(comment);
        }
        return "redirect:/comments/post/" + postId;
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, HttpSession session) {
        Optional<Comment> comment = commentService.findById(id);
        if(comment.isEmpty()) {
            return "redirect:/posts" ;
        }
        Member loginMember = (Member) session.getAttribute("loginMember");
        if(loginMember != null && loginMember.getId().equals(comment.get().getMember().getId())) {
            commentService.deleteById(id);
        }
        return "redirect:/comments/post/" + comment.get().getPost().getId();
    }

}
