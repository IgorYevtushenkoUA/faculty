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

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    StudentHasCourseService studentHasCourseService;

    @Autowired
    TopicService topicService;

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
        model.addAttribute("classes", setClass(sortType));
        model.addAttribute("topicList", topicService.findAll());
        return "courses";
    }

    private List<String> setClass(String sortType) {
        if (sortType.equals("ASC")) return List.of("btn btn-primary", "btn btn-outline-danger");
        return List.of("btn btn-outline-primary", "btn btn-danger");
    }

    @GetMapping("/courses/{courseId}")
    public String courseGet(Model model,
                            @PathVariable("courseId") Integer courseId) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseInfoDto", buildCourseInfoDto(courseId));
        model.addAttribute("canEnroll", isCanEnroll());
        model.addAttribute("showRegisterBtn", showRegisterBtn());
        return "course";
    }

    private boolean isCanEnroll() {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj.equals("anonymousUser"))
            return false;
        return true;
    }

    private boolean showRegisterBtn(){
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj.equals("anonymousUser"))
            return true;
        User user = (User) obj;
        return user.getRole().getName().equals(ROLE.ROLE_STUDENT.name()) && user.isEnabled();
    }

    @PostMapping("/courses/{courseId}")
    public String coursePost(@PathVariable("courseId") Integer courseId) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        studentHasCourseService.enrollStudentToCourse(student.getId(), courseId);
        return "redirect:/courses/{courseId}";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("role", getRole());
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
