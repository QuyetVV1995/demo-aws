package com.example.demo.controller;

import com.example.demo.controller.request.CommentRequest;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.exception.PostException;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public String handlePostComment(@ModelAttribute("comment") CommentRequest commentRequest) throws PostException {
        Optional<User> optionalUser = userService.findbyEmail(userService.getUsername());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            postService.addComment(commentRequest, user.getId());
            return "redirect:/post/" + commentRequest.getPost_id();
        }else{
            return "error";
        }
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") long id){
        // User dang nhap
        Optional<User> principalUser = userService.findbyEmail(userService.getUsername());
        // User comment
        Optional<Comment> optionalComment = commentService.findById(id);
        if(principalUser.get().getEmail() == optionalComment.get().getUser().getEmail()){
            Comment comment = optionalComment.get();
            commentService.deleteById(id);
            return "redirect:/post/" + comment.getPost().getId();
        }else{
            return "index";
        }

    }
}
