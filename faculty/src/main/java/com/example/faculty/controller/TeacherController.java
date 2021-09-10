package com.example.faculty.controller;

import com.example.faculty.dto.CourseStudentsDto;
import com.example.faculty.dto.StudentInfoDto;
import com.example.faculty.entety.StudentHasCourse;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.StudentHasCourseService;
import com.example.faculty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // для чого ця сторінка, якщо вся інфа на teacher/{teacherId}/courses -> о суті зайва та непотрібна сторінка
    @GetMapping("/teacher/{id}")
    public String teacherGet(Model model,
                             @PathVariable("id") Integer id) {
        model.addAttribute("id", id);
        return "/teacher/teacherPersonalPage";
    }

    @GetMapping("/teacher/{teacherId}/courses")
    public String teacherCoursesGet(Model model,
                                    @PathVariable("teacherId") Integer teacherId) {
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("courses", courseService.findAllTeacherCourses(teacherId));
        return "/teacher/coursesList";
    }

    @GetMapping("/teacher/{teacherId}/courses/{courseId}")
    public String teacherCourseInfoGet(Model model,
                                       @PathVariable("teacherId") Integer teacherId,
                                       @PathVariable("courseId") Integer courseId,
                                       @RequestParam(value = "year", defaultValue = "2021") Integer year) {
        model.addAttribute("courseStudentsDto", buildCourseStudentsDto(teacherId, courseId, year));
        return "/teacher/courseInfo";
    }

    @GetMapping("/teacher/{teacherId}/courses/{courseId}/student/{studentId}")
    public String teacherCoureStudentInfoGet(Model model,
                                             @PathVariable("courseId") Integer courseId,
                                             @PathVariable("studentId") Integer studentId) {
        model.addAttribute("student", userService.findStudentInfoByIdAndCourseId(courseId, studentId));
        return "/teacher/studentInfo";
    }

    private CourseStudentsDto buildCourseStudentsDto(int teacherId, int courseId, int year) {
        CourseStudentsDto courseStudentsDto = new CourseStudentsDto();
        courseStudentsDto.setTeacherId(teacherId);
        courseStudentsDto.setYear(year);
        List<StudentInfoDto> studentInfoDtoList = new ArrayList<>();
        List<StudentHasCourse> studentHasCourseList = studentHasCourseService.findAllStudentsByCourseAndYear(courseId, year);
        for(StudentHasCourse studentHasCourse : studentHasCourseList){
            StudentInfoDto studentInfoDto = new StudentInfoDto();
            studentInfoDto.setStudent(userService.findStudentById(studentHasCourse.getStudentCourseId().getStudentId()));
            studentInfoDto.setMark(studentHasCourse.getMark());
            studentInfoDto.setRecordingTime(studentHasCourse.getRecordingTime());
            studentInfoDtoList.add(studentInfoDto);
        }
        courseStudentsDto.setStudents(studentInfoDtoList);
        return courseStudentsDto;
    }

}
