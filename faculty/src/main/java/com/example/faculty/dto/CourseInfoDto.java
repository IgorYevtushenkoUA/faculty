package com.example.faculty.dto;

import com.example.faculty.entety.Course;
import com.example.faculty.entety.StudentHasCourse;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.StudentHasCourseService;
import com.example.faculty.service.UserService;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CourseInfoDto {
    Course course;
    List<StudentInfoDto> enrolledStudent;

    public static CourseInfoDto buildCourseInfoDto(int courseId,
                                                   UserService userService,
                                                   CourseService courseService,
                                                   StudentHasCourseService studentHasCourseService) {
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
