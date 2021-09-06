package com.example.faculty.controller;

import com.example.faculty.entety.Course;
import com.example.faculty.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.faculty.util.Constants.DEFAULT_PAGE_SIZE;
import static com.example.faculty.util.Constants.EMPTY_INTEGER_VALUE;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

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

    @GetMapping("/courses/{id}")
    public String courseGet(Model model,
                            @PathVariable("id") Integer id) {
        model.addAttribute("course",courseService.findById(id));
        return "course";
    }


}
