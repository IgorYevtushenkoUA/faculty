package com.example.faculty.controller;

import com.example.faculty.dto.CourseStudentsDto;
import com.example.faculty.dto.StudentInfoDto;
import com.example.faculty.entety.StudentHasCourse;
import com.example.faculty.entety.Teacher;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.StudentHasCourseService;
import com.example.faculty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.faculty.util.Methods.getRole;

@Controller
public class TeacherController {

    CourseStudentsDto courseStudentsDto;

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    StudentHasCourseService studentHasCourseService;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("role", getRole());
    }

    @GetMapping("/teacher")
    public String teacherCoursesGet(Model model) {
        Teacher teacher = (Teacher) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", courseService.findAllTeacherCourses(teacher.getId()));
        return "/users/teacher/coursesList";
    }

    @GetMapping("/teacher/{courseId}")
    public String teacherCourseInfoGet(Model model,
                                       @PathVariable("courseId") Integer courseId,
                                       @RequestParam(value = "year", defaultValue = "2021") Integer year) {
        Teacher teacher = (Teacher) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("courseStudentsDto", courseStudentsDto.buildCourseStudentsDto(teacher.getId(), courseId, year, userService, courseService, studentHasCourseService));
        return "/users/teacher/courseInfo";
    }

    @PostMapping("/teacher/{courseId}/student/{studentId}")
    public String teacherCourseStudentInfoGet(Model model,
                                              @PathVariable("courseId") Integer courseId,
                                              @PathVariable("studentId") Integer studentId,
                                              @RequestParam("mark") int mark) {
        StudentHasCourse studentHasCourse = studentHasCourseService.findByStudentAndCourse(studentId, courseId);
        studentHasCourse.setMark(mark);
        studentHasCourseService.save(studentHasCourse);
        model.addAttribute("student", userService.findStudentInfoByIdAndCourseId(courseId, studentId));
        return "redirect:/teacher/{courseId}";
    }

}
