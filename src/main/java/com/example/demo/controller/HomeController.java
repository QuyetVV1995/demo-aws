package com.example.demo.controller;

import com.example.demo.controller.request.SearchRequest;
import com.example.demo.entity.Post;
import com.example.demo.entity.Role;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.service.PostService;
import com.example.demo.service.TagService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;


    @GetMapping(value = {"/", "/{page}"})
    public String homepage(@PathVariable(value="page", required = false) Integer page, Model model){
        Optional<User> optionalUser = userService.findbyEmail(userService.getUsername());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("searchRequest", new SearchRequest());

        if(auth.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"))){
            if (optionalUser.isPresent()) {

                User user = optionalUser.get();
                model.addAttribute("user", user);

                List<User> userList = userService.findAll();
                model.addAttribute("userList", userList);
                long totalN1 = postService.totalPostofCategory("N1").size();
                long totalN2 = postService.totalPostofCategory("N2").size();
                long totalN3 = postService.totalPostofCategory("N3").size();
                long totalN4 = postService.totalPostofCategory("N4").size();
                long totalN5 = postService.totalPostofCategory("N5").size();
                long totalITJP = postService.totalPostofCategory("it-japanese").size();
                long totalJava = postService.totalPostofCategory("java-basic").size();
                long totalSpring = postService.totalPostofCategory("spring-boot").size();

                model.addAttribute("totalN5", totalN5);
                model.addAttribute("totalN4", totalN4);
                model.addAttribute("totalN3", totalN3);
                model.addAttribute("totalN2", totalN2);
                model.addAttribute("totalN1", totalN1);
                model.addAttribute("totalITJP", totalITJP);
                model.addAttribute("totalJava", totalJava);
                model.addAttribute("totalSpring", totalSpring);

            }
           return "admin/index";
        }else {         // khong phai ADMIN
            if (page == null) {
                page = 0;
            }
            Page<Post> pagePosts = postService.findAllPaging(page, 12); //Mỗi page 12 Post

            List<Post> posts = pagePosts.getContent();
            model.addAttribute("listPost", posts);

            //Sinh ra cấu trúc dữ liệu phân trang
            List<Paging> pagings = Paging.generatePages(page, pagePosts.getTotalPages());
            model.addAttribute("pagings", pagings);

            if (optionalUser.isPresent()) {     // da dang nhap
                User user = optionalUser.get();
                model.addAttribute("user", user);
            }else{
                model.addAttribute("user", new User());
            }
            return "index";
        }
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
