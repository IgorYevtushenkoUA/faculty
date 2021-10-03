package com.example.faculty.controller;

import com.example.faculty.entety.Student;
import com.example.faculty.enums.ROLE;
import com.example.faculty.service.RoleService;
import com.example.faculty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static com.example.faculty.util.Methods.getRole;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("role", getRole());
    }

    @GetMapping("/register")
    public String registrationGet(Model model) {
        model.addAttribute("userForm", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String registrationPost(@ModelAttribute("userForm") @Valid Student userForm,
                                   BindingResult bindingResult,
                                   Model model) {

        System.out.println("bindingResult.hasErrors() :" + bindingResult.hasErrors());

        if(bindingResult.hasErrors()){
            System.out.println("error with name");

            return "register";
        }

        userForm.setEnable(true);

        if (userService.findByEmail(userForm.getEmail()) != null) {
            System.out.println("user should change email");
            model.addAttribute("message", "Please enter another email");
            return "register";
        }

        if (!userService.save(userForm, roleService.findByName(ROLE.ROLE_STUDENT.name()))) {
            model.addAttribute("userError", "Registration error");
            return "register";
        }
        return "redirect:/login";
    }

}
