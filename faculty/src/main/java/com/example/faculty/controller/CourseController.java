package com.example.faculty.controller;

import com.example.faculty.dto.CourseInfoDto;
import com.example.faculty.dto.StudentInfoDto;
import com.example.faculty.entety.Student;
import com.example.faculty.entety.StudentHasCourse;
import com.example.faculty.entety.User;
import com.example.faculty.enums.ROLE;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.StudentHasCourseService;
import com.example.faculty.service.TopicService;
import com.example.faculty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.faculty.util.Methods.getRole;

@Controller
public class CourseController {

    CourseInfoDto courseInfoDto;

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    StudentHasCourseService studentHasCourseService;

    @Autowired
    TopicService topicService;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("role", getRole());
    }
    
    @GetMapping("/")
    public String coursesGet(
            Model model,
            @RequestParam(value = "courseName", defaultValue = "") String courseName,
            @RequestParam(value = "duration", defaultValue = "0") int duration,
            @RequestParam(value = "capacity", defaultValue = "0") int capacity,
            @RequestParam(value = "topic", defaultValue = "...") String topic,
            @RequestParam(value = "teacher", defaultValue = "") String teacher,
            @RequestParam(value = "sortType", defaultValue = "ASC") String sortType,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size) {

        model.addAttribute("courseName", courseName);
        model.addAttribute("duration", duration);
        model.addAttribute("capacity", capacity);
        model.addAttribute("topic", topic);
        model.addAttribute("teacher", teacher);
        model.addAttribute("sortType", sortType);
        model.addAttribute("courses", courseService.getPage(courseName, duration, capacity, topic, teacher, pageNumber, size, sortType));
        model.addAttribute("classes", setBtnClass(sortType));
        model.addAttribute("topicList", topicService.findAll());
        return "courses";
    }

    @GetMapping("/courses/{courseId}")
    public String courseGet(Model model,
                            @PathVariable("courseId") Integer courseId) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseInfoDto", courseInfoDto.buildCourseInfoDto(courseId, userService, courseService, studentHasCourseService));
        model.addAttribute("isEnrolled", isEnrolled(courseId));
        return "course";
    }

    @PostMapping(value = "/courses/{courseId}", params = "action=enroll")
    public String coursePostEnroll(@PathVariable("courseId") Integer courseId) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        studentHasCourseService.enrollStudentToCourse(student.getId(), courseId);
        return "redirect:/courses/{courseId}";
    }

    @PostMapping(value = "/courses/{courseId}", params = "action=drop_out")
    public String coursePostDropOut(@PathVariable("courseId") Integer courseId) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        studentHasCourseService.dropOutFromCourse(student.getId(), courseId);
        return "redirect:/courses/{courseId}";
    }

    private List<String> setBtnClass(String sortType) {
        if (sortType.equals("ASC")) return List.of("btn btn-primary", "btn btn-outline-danger");
        return List.of("btn btn-outline-primary", "btn btn-danger");
    }

    private boolean isEnrolled(int courseId) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!obj.equals("anonymousUser")) {
            User user = (User) obj;
            return studentHasCourseService.findByStudentAndCourse(user.getId(), courseId) != null;
        }
        return false;
    }
}
