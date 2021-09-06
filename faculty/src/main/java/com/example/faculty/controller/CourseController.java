package com.example.faculty.controller;

import com.example.faculty.entety.Course;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.StudentHasCourseService;
import com.example.faculty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.faculty.util.Constants.DEFAULT_PAGE_SIZE;

@Controller
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    StudentHasCourseService studentHasCourseService;

    @GetMapping("/courses")
    public String coursesGet(
            Model model,
            @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @RequestParam(value = "courseName", defaultValue = "") String courseName,
            @RequestParam(value = "duration", defaultValue = "-1") Integer duration,
            @RequestParam(value = "capacity", defaultValue = "-1") Integer capacity,
            @RequestParam(value = "topic", defaultValue = "") String topic,
            @RequestParam(value = "teacher", defaultValue = "") String teacher
    ) {
        Page<Course> pages = courseService.setCourses(courseName, duration, capacity, topic, teacher, pageable);
        model.addAttribute("number", pages.getNumber());
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalElements", pages.getTotalElements());
        model.addAttribute("pageNumbers", IntStream.rangeClosed(1, pages.getTotalPages()).boxed().collect(Collectors.toList()));
        model.addAttribute("size", pages.getSize());
        model.addAttribute("courses", pages.getContent());
        return "courses";
    }

    @GetMapping("/courses/{courseId}")
    public String courseGet(Model model,
                            @PathVariable("courseId") Integer courseId) {
        model.addAttribute("course", courseService.findById(courseId));
        model.addAttribute("courseId", courseId);
        return "course";
    }

    @PostMapping("/courses/{courseId}")
    public String coursePost(@PathVariable("courseId") Integer courseId) {
        //todo change{studentId} to real student
        int studentId = 7;
        studentHasCourseService.enrollStudentToCourse(studentId, courseId);
        return "redirect:/courses/{courseId}";
    }

}
