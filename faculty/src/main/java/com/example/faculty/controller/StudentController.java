package com.example.faculty.controller;

import com.example.faculty.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    @Autowired
    CourseService courseService;

    @GetMapping("/student/{id}")
    public String studentGet(Model model,
                             @PathVariable("id") Integer id,
                             @RequestParam(value = "type", defaultValue = "progress") String type) {
        model.addAttribute("id", id);
        model.addAttribute("type", type);
        model.addAttribute("courses", courseService.findAllStudentCoursesByType(id,type));
        return "/student/studentPersonalPage";
    }

}
