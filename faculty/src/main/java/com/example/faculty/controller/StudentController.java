package com.example.faculty.controller;

import com.example.faculty.dto.StudentCourseInfoDto;
import com.example.faculty.entety.Course;
import com.example.faculty.entety.Student;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.StudentHasCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.example.faculty.util.Methods.getRole;

@Controller
public class StudentController {

    StudentCourseInfoDto studentCourseInfoDto;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentHasCourseService studentHasCourseService;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("role", getRole());
    }

    @GetMapping("/student")
    public String studentGet(Model model,
                             @RequestParam(value = "type", defaultValue = "progress") String type) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("type", type);
        model.addAttribute("courses", studentCourseInfoDto.buildStudentCourseInfoDto(student.getId(),
                courseService.findAllStudentCoursesByType(student.getId(), type), studentHasCourseService));

        return "/users/student/studentPersonalPage";
    }

}
