package com.example.faculty.dto;

import com.example.faculty.entety.Student;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfoDto {

    Student student;
    Integer mark;
    Timestamp recordingTime;

}
