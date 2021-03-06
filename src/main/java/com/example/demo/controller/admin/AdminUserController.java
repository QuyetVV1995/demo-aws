package com.example.demo.controller.admin;

import com.example.demo.controller.request.SearchRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/manage-user")
    public String manageUser(Model model){

        Optional<User> optionalUser = userService.findbyEmail(userService.getUsername());
        User user = optionalUser.get();
        model.addAttribute("user", user);
        model.addAttribute("searchRequest", new SearchRequest());
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "admin/manage_user";
    }

    @GetMapping("/admin/create-user")
    public String createUser(Model model){
        Optional<User> optionalUser = userService.findbyEmail(userService.getUsername());
        User user = optionalUser.get();
        model.addAttribute("user", user);
        model.addAttribute("searchRequest", new SearchRequest());
        model.addAttribute("newUser", new User());
        return "admin/create_user";
    }

    @PostMapping("/admin/save-user")
    public String createUserHandle(@ModelAttribute("newUser") User user){
        userService.saveNewUser(user);
        return "redirect:/admin/manage-user";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model){
        Optional<User> optionalUser = userService.findbyEmail(userService.getUsername());
        User user = optionalUser.get();
        model.addAttribute("user", user);
        model.addAttribute("searchRequest", new SearchRequest());
        Optional<User> opUser = userService.findById(id);
        model.addAttribute("newUser", opUser.get());
        return "admin/edit_user";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id){
        userService.deleteById(id);
        return "redirect:/admin/manage-user";
    }
}
