package com.example.faculty.dto;

import com.example.faculty.entety.Course;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CourseStudentsDto {
    int teacherId;
    Course course;
    List<StudentInfoDto> students;
    int year;
}
