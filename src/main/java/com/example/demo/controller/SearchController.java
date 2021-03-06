package com.example.demo.controller;

import com.example.demo.controller.request.SearchRequest;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;


    @PostMapping("/search")
    public String handleSearch(@ModelAttribute("searchRequest") SearchRequest searchRequest, Model model){
        Optional<User> optionalUser = userService.findbyEmail(userService.getUsername());
        if (optionalUser.isPresent()) {     // da dang nhap
            User user = optionalUser.get();
            model.addAttribute("user", user);
        }else{
            model.addAttribute("user", new User());
        }
        List<Post> posts = postService.searchPost(searchRequest.getTerm(), 5, 0);
        model.addAttribute("posts", posts);
        return "searchResult";
    }

    @GetMapping("/search/index")
    public String reindexFullText(Model model){
        postService.reindexFullText();
        return "redirect:/";
    }
}
