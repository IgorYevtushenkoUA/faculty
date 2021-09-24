package com.example.faculty.dto;

import com.example.faculty.entety.Course;
import com.example.faculty.service.StudentHasCourseService;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseInfoDto {
    int courseId;
    String courseName;
    Integer mark;

    public static List<StudentCourseInfoDto> buildStudentCourseInfoDto(int studentId,
                                                                 List<Course> courses,
                                                                 StudentHasCourseService studentHasCourseService) {
        List<StudentCourseInfoDto> studentCourseInfoDtoList = new ArrayList<>();
        for (Course course : courses) {
            studentCourseInfoDtoList.add(new StudentCourseInfoDto(course.getId(), course.getName(),
                    studentHasCourseService.getMarkForCourse(studentId, course.getId())));
        }
        return studentCourseInfoDtoList;
    }
}
