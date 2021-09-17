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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    StudentHasCourseService studentHasCourseService;

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
        model.addAttribute("courseStudentsDto", buildCourseStudentsDto(teacher.getId(), courseId, year));
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

    private CourseStudentsDto buildCourseStudentsDto(int teacherId, int courseId, int year) {
        CourseStudentsDto courseStudentsDto = new CourseStudentsDto();
        courseStudentsDto.setTeacherId(teacherId);
        courseStudentsDto.setYear(year);
        List<StudentInfoDto> studentInfoDtoList = new ArrayList<>();
        List<StudentHasCourse> studentHasCourseList = studentHasCourseService.findAllStudentsByCourseAndYearAndName(courseId, year);
        for (StudentHasCourse studentHasCourse : studentHasCourseList) {
            StudentInfoDto studentInfoDto = new StudentInfoDto();
            studentInfoDto.setStudent(userService.findStudentById(studentHasCourse.getStudentCourseId().getStudentId()));
            studentInfoDto.setMark(studentHasCourse.getMark());
            studentInfoDto.setRecordingTime(studentHasCourse.getRecordingTime());
            studentInfoDtoList.add(studentInfoDto);
        }
        courseStudentsDto.setCourse(courseService.findById(courseId));
        courseStudentsDto.setStudents(studentInfoDtoList);
        return courseStudentsDto;
    }

}
