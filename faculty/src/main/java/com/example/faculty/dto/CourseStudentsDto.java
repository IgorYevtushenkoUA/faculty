package com.example.faculty.dto;

import com.example.faculty.entety.Course;
import com.example.faculty.entety.StudentHasCourse;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.StudentHasCourseService;
import com.example.faculty.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CourseStudentsDto {
    int teacherId;
    Course course;
    List<StudentInfoDto> students;
    int year;

    public static CourseStudentsDto buildCourseStudentsDto(int teacherId,
                                                    int courseId,
                                                    int year,
                                                    UserService userService,
                                                    CourseService courseService,
                                                    StudentHasCourseService studentHasCourseService) {
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
