package com.example.faculty.dto;

import com.example.faculty.entety.Course;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CourseInfoDto {
    Course course;
    List<StudentInfoDto> enrolledStudent;
}
