package com.example.faculty.controller;

import com.example.faculty.entety.Student;
import com.example.faculty.entety.User;
import com.example.faculty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String registrationGet(Model model) {
        model.addAttribute("userForm", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String registrationPost(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        System.out.println(userForm);
        System.out.println("we are in post register");
        if (bindingResult.hasErrors())
            return "register";

        if (!userService.save(userForm)) {
            model.addAttribute("userError", "Registration error");
            return "register";
        }
        return "redirect:/courses";
    }

}
