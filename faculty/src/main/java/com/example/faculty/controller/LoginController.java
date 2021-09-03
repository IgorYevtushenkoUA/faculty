package com.example.faculty.controller;

import com.example.faculty.entety.Student;
import com.example.faculty.entety.User;
import com.example.faculty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("student", new Student());
        return "login";
    }


    @PostMapping("/login-processing")
    public String loginProcessingPost(@ModelAttribute Student student){
        User s = userService.findByEmail(student.getEmail());
        System.out.println("login post");
        if (s == null) {
            System.out.println("error");
            return "login";
        }
        System.out.println("login");
        return "redirect:/courses";
    }

}
