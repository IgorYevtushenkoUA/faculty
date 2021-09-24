package com.example.faculty.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseInfoDto {
    int courseId;
    String courseName;
    Integer mark;
}
