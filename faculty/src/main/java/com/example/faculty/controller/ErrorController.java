package com.example.faculty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.faculty.util.Methods.getRole;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String errorGet(Model model,
                           @RequestParam(value = "error", defaultValue = "Something went wrong") String message) {
        model.addAttribute("message", message);
        return "error";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("role", getRole());
    }
}
