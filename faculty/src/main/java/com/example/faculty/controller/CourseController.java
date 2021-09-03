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
        Page<Course> pages = setCourses(courseName, duration, capacity, topic, teacher, pageable);
        model.addAttribute("number", pages.getNumber());
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalElements", pages.getTotalElements());
        model.addAttribute("pageNumbers", IntStream.rangeClosed(1,pages.getTotalPages()).boxed().collect(Collectors.toList()));
        model.addAttribute("size", pages.getSize());
        model.addAttribute("courses", pages.getContent());
        return "courses";
    }

    private Page<Course> setCourses(String courseName, Integer duration, Integer capacity, String topic, String teacher, Pageable pageable) {
        if (courseName.isEmpty() && duration == EMPTY_INTEGER_VALUE && capacity == EMPTY_INTEGER_VALUE
                && topic.isEmpty() && teacher.isEmpty()) {
            return courseService.findAll(pageable);
        }
       return courseService.findAllByParams(setCourseNameParam(courseName), setDurationParam(duration), setCapacityParam(capacity),
                setTopicsParam(topic), setTeacherNameParam(teacher), pageable);
    }

    private List<String> setCourseNameParam(String courseName) {
        return courseName.isEmpty() ? courseService.findAllCourseNames() : courseService.findCourseNameByName(courseName);
    }

    private List<Integer> setDurationParam(Integer duration) {
        return duration == EMPTY_INTEGER_VALUE ? courseService.findAllDurations() : List.of(duration);
    }

    private List<Integer> setCapacityParam(Integer capacity) {
        return capacity == EMPTY_INTEGER_VALUE ? courseService.findAllCapacities() : List.of(capacity);
    }

    private List<String> setTopicsParam(String topics) {
        return topics.isEmpty() ? courseService.findAllTopics() : List.of(topics);
    }

    private List<Integer> setTeacherNameParam(String teacherName) {
        return teacherName.isEmpty() ? courseService.findAllTeacherNames() : courseService.findTeacherIdByName(teacherName);
    }
}
