package com.example.faculty.controller;

import com.example.faculty.dto.CourseInfoDto;
import com.example.faculty.dto.StudentInfoDto;
import com.example.faculty.entety.Course;
import com.example.faculty.entety.Student;
import com.example.faculty.entety.StudentHasCourse;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.StudentHasCourseService;
import com.example.faculty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
            @RequestParam(value = "duration", defaultValue = "0") int duration,
            @RequestParam(value = "capacity", defaultValue = "0") int capacity,
            @RequestParam(value = "topic", defaultValue = "") String topic,
            @RequestParam(value = "teacher", defaultValue = "") String teacher,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size) {

        model.addAttribute("courseName", courseName);
        model.addAttribute("duration", duration);
        model.addAttribute("capacity", capacity);
        model.addAttribute("topic", topic);
        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", courseService.getPage(courseName, duration, capacity, topic, teacher, pageNumber, size));
        return "courses";
    }


    @GetMapping("/courses/{courseId}")
    public String courseGet(Model model,
                            @PathVariable("courseId") Integer courseId) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseInfoDto", buildCourseInfoDto(courseId));
        model.addAttribute("canEnroll", !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"));
        return "course";
    }

    @PostMapping("/courses/{courseId}")
    public String coursePost(@PathVariable("courseId") Integer courseId) {
        //todo change{studentId} to real student
        int studentId = 7;
        studentHasCourseService.enrollStudentToCourse(studentId, courseId);
        return "redirect:/courses/{courseId}";
    }

    private CourseInfoDto buildCourseInfoDto(int courseId) {
        CourseInfoDto courseInfoDto = new CourseInfoDto();
        List<StudentInfoDto> enrolledStudent = new ArrayList<>();
        for (StudentHasCourse studentHasCourse : studentHasCourseService.findAllStudentsByCourse(courseId)) {
            enrolledStudent.add(
                    new StudentInfoDto(
                            userService.findStudentById(studentHasCourse.getStudentCourseId().getStudentId()),
                            studentHasCourse.getMark(),
                            studentHasCourse.getRecordingTime()));
        }
        courseInfoDto.setCourse(courseService.findById(courseId));
        courseInfoDto.setEnrolledStudent(enrolledStudent);
        return courseInfoDto;
    }

}
